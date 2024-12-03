package com.personal.dailyActivities.service.impl;

import com.personal.dailyActivities.auth.entity.UserRegister;
import com.personal.dailyActivities.auth.repository.UserRegisterRepository;
import com.personal.dailyActivities.dto.YearlyAchievementReportDTO;
import com.personal.dailyActivities.entity.Activities;
import com.personal.dailyActivities.enums.Category;
import com.personal.dailyActivities.enums.Status;
import com.personal.dailyActivities.repositroy.ActivitiesRepository;
import com.personal.dailyActivities.repositroy.HobbiesRepository;
import com.personal.dailyActivities.service.UserService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRegisterRepository userRepository;
    private final ActivitiesRepository activitiesRepository;
    private final HobbiesRepository hobbiesRepository;

    public UserServiceImpl(UserRegisterRepository userRepository, ActivitiesRepository activitiesRepository, HobbiesRepository hobbiesRepository) {
        this.userRepository = userRepository;
        this.activitiesRepository = activitiesRepository;
        this.hobbiesRepository = hobbiesRepository;
    }

    @Override
    public YearlyAchievementReportDTO getYearlyAchievementReport(int year, Long userId) {
        String userName = userRepository.findById(Math.toIntExact(userId))
                .map(UserRegister::getUserName).orElseThrow();
        List<Activities> activities = activitiesRepository.findActivitiesByUserId(userId);
        //List<Hobbies> hobbies = hobbiesRepository.findHobbiesByUserId(userId);

        YearlyAchievementReportDTO report = new YearlyAchievementReportDTO();
        report.setUserName(userName);
        report.setYear(year);
        report.setTotalActivities((int) activities.stream().distinct().count());
        report.setCompletedActivities((int) activities.stream()
                .filter(act -> Objects.equals(act.getStatus(), Status.Completed))
                .count());
        //report.setHobbiesMastered(hobbies.stream().filter(hob -> hob.));

        Map<Category, String> timeSpentSummary = activities.stream().collect(
                        Collectors.groupingBy(Activities::getCategory,
                                Collectors.summingLong(a -> Duration.between(a.getStartTime(), a.getEndTime()).toHours()))
                ).entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().toString() + " hrs"));
        report.setTimeSpentSummary(timeSpentSummary);

        return report;
    }
}
