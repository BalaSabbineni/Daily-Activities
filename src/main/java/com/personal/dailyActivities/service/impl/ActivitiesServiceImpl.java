package com.personal.dailyActivities.service.impl;

import com.personal.dailyActivities.auth.entity.UserRegister;
import com.personal.dailyActivities.auth.repository.UserRegisterRepository;
import com.personal.dailyActivities.dto.*;
import com.personal.dailyActivities.entity.Activities;
import com.personal.dailyActivities.enums.Category;
import com.personal.dailyActivities.enums.Priority;
import com.personal.dailyActivities.enums.Status;
import com.personal.dailyActivities.repositroy.ActivitiesRepository;
import com.personal.dailyActivities.service.ActivitiesService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ActivitiesServiceImpl implements ActivitiesService {

    private final ActivitiesRepository activitiesRepository;
    private final UserRegisterRepository userRepository;

    public ActivitiesServiceImpl(ActivitiesRepository activitiesRepository, UserRegisterRepository userRepository) {
        this.activitiesRepository = activitiesRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Object createActivities(UserRegister user, List<ActivityRequest> request) {

        var conflictsDTO = request.stream().flatMap(activity1 -> request.stream()
                .sorted(Comparator.comparing(ActivityRequest::getStartTime))
                .filter(activity2 -> !Objects.equals(activity1, activity2)
                        && activity1.getStartTime().isBefore(activity2.getStartTime())
                        && !activity1.getEndTime().isBefore(activity2.getStartTime())
                        && !activity2.getStartTime().isAfter(activity1.getEndTime())
                )
                .map(activity2 -> {
                    ActivitiesConflictDTO.ConflictsDTO conflicts = new ActivitiesConflictDTO.ConflictsDTO();
                    conflicts.setActivity1(activity1.getName());
                    conflicts.setActivity2(activity2.getName());
                    conflicts.setConflictType("Time  Overlap");
                    conflicts.setSuggestedResolution(String.format("Reschedule %s to start at %s",
                            activity2.getName(), suggestResolution(activity1.getEndTime())));
                    return conflicts;
                })).toList();

        if (conflictsDTO.isEmpty()) {
            List<Activities> newActivities = request.stream().map(req -> {
                Activities activities = new Activities();
                activities.setName(req.getName());
                activities.setDescription(req.getDescription());
                activities.setCategory(req.getCategory());
                activities.setStatus(req.getStatus());
                activities.setPriority(req.getPriority());
                activities.setTimeOfDay(req.getTimeOfDay());
                activities.setIsRecurring(req.getIsRecurring());
                activities.setRecurrencePattern(req.getRecurrencePattern());
                activities.setStartTime(req.getStartTime());
                activities.setEndTime(req.getEndTime());
                activities.setUser(user);
                return activities;
            }).toList();

            return activitiesRepository.saveAll(newActivities);
        }
        return conflictsDTO;
    }

    @Override
    public Object getActivitiesByTimeOfDay(Long userId) {
        var activities = activitiesRepository.findActivitiesByUserId(userId);

        return activities.stream().collect(Collectors.groupingBy(Activities::getTimeOfDay));
    }

    @Override
    public Object getActivityBySearch(Category category, Priority priority) {
        return priority == null ? activitiesRepository.findActivitiesByCategory(category) :
                activitiesRepository.findActivitiesByCategoryAndPriority(category, priority);
    }

    @Override
    @Transactional
    public List<Activities> updateActivityStatus(List<Integer> ids, String newStatus) {
        List<Activities> activities = ids.stream().map(act -> {
            var activity = activitiesRepository.findById(Long.valueOf(act)).orElseThrow();
            activity.setStatus(Status.valueOf(newStatus));
            return activity;
        }).toList();
        return activitiesRepository.saveAll(activities);
    }

    @Override
    public WeeklyActivitySummaryDTO weeklyActivitySummaryDTO(Long userId) {
        UserRegister user = userRepository.findById(Math.toIntExact(userId)).orElseThrow();
        List<Activities> activities = activitiesRepository.findActivitiesByUserId(userId);

        var weeklyActivitySummaryDTO = WeeklyActivitySummaryDTO.builder();
        weeklyActivitySummaryDTO.userName(user.getUserName());
        weeklyActivitySummaryDTO.summary(getSummary(activities));
        weeklyActivitySummaryDTO.activitiesByDay(getActivitiesByDay(activities));
        return weeklyActivitySummaryDTO.build();
    }

    @Override
    public ActivitiesConflictDTO checkConflicts(List<ActivityRequest> request) {
        return null;
    }

    private String suggestResolution(LocalDateTime endTime) {
        var time = endTime.plusMinutes(15);
        return String.format("%d:%d", time.getHour(), time.getMinute());
    }

    private Map<DayOfWeek, List<ActivityDetailsByDay>> getActivitiesByDay(List<Activities> activities) {
        return activities.stream()
                .collect(Collectors.groupingBy(act -> act.getStartTime().getDayOfWeek(),
                        Collectors.mapping(a -> {
                            ActivityDetailsByDay activity = new ActivityDetailsByDay();
                            activity.setName(a.getName());
                            activity.setTimeSpent(getTimeSpent(a.getStartTime(), a.getEndTime()));
                            activity.setStatus(a.getStatus().name());
                            return activity;
                        }, Collectors.toList())
                ));

    }

    private Summary getSummary(List<Activities> activities) {

        return Summary.builder()
                .totalActivities(activities.size())
                .completedActivities((int) activities.stream()
                        .filter(act -> Objects.equals(act.getStatus(), Status.Completed))
                        .count())
                .mostActiveDay(getDayFromTime(activities))
                .categorySummary(getCategorySummary(activities)).build();
    }

    private Map<Category, CategorySummaryByType> getCategorySummary(List<Activities> activities) {
        return activities.stream()
                .collect(Collectors.groupingBy(
                        Activities::getCategory,
                        Collectors.collectingAndThen(
                                Collectors.summingLong(act -> Duration.between(act.getStartTime(), act.getEndTime()).toMinutes()),
                                timeSpent -> {
                                    CategorySummaryByType type = new CategorySummaryByType();
                                    long hours = timeSpent / 60;
                                    long minutes = timeSpent % 60;

                                    type.setTotalTimeSpent(String.format("%d hours %d minutes", hours, minutes));
                                    return type;
                                }
                        )
                ));
    }

    private DayOfWeek getDayFromTime(List<Activities> activities) {
        return activities.stream()
                .collect(Collectors.groupingBy(
                        act -> act.getStartTime().getDayOfWeek(),
                        Collectors.summingLong(t -> Duration.between(t.getStartTime(), t.getEndTime()).toMinutes())
                ))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow();
    }

    private String getTimeSpent(LocalDateTime startTime, LocalDateTime endTime) {
        long timeSpent = Duration.between(startTime, endTime).toMinutes();
        long hours = timeSpent / 60;
        long minutes = timeSpent % 60;
        return String.format("%d hours %d minutes", hours, minutes);
    }

}
