package com.personal.dailyActivities.controller;

import com.personal.dailyActivities.auth.entity.UserRegister;
import com.personal.dailyActivities.auth.repository.UserRegisterRepository;
import com.personal.dailyActivities.dto.ActivitiesConflictDTO;
import com.personal.dailyActivities.dto.ActivityRequest;
import com.personal.dailyActivities.dto.WeeklyActivitySummaryDTO;
import com.personal.dailyActivities.entity.Activities;
import com.personal.dailyActivities.enums.Category;
import com.personal.dailyActivities.enums.Priority;
import com.personal.dailyActivities.repositroy.ActivitiesRepository;
import com.personal.dailyActivities.service.ActivitiesService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class ActivitiesController {
    private final UserRegisterRepository userRepository;
    private final ActivitiesRepository activitiesRepository;
    private final ActivitiesService activitiesService;

    public ActivitiesController(UserRegisterRepository userRepository, ActivitiesRepository activitiesRepository, ActivitiesService activitiesService) {
        this.userRepository = userRepository;
        this.activitiesRepository = activitiesRepository;
        this.activitiesService = activitiesService;
    }

    @PostMapping("createActivities")
    public Object createActivities(Authentication authentication, @RequestBody List<ActivityRequest> request) {
        // using Authentication token, we can get respective user details.
        var user = getUser(authentication);

        return activitiesService.createActivities(user, request);
    }

    @GetMapping("getActivitiesByTimeOfDay")
    public Object getActivitiesByTimeOfDay(Authentication authentication) {
        var user = getUser(authentication);
        return activitiesService.getActivitiesByTimeOfDay(Long.valueOf(user.getId()));
    }


    @GetMapping("activities/search")
    public Object getActivityBySearch(@RequestParam(required = true) Category category,
                                      @RequestParam(required = false) Priority priority) {
        return activitiesService.getActivityBySearch(category, priority);
    }

    @PutMapping("activities/{newStatus}")
    private List<Activities> updateActivityStatus(@RequestBody List<Integer> ids, @PathVariable String newStatus) {

        return activitiesService.updateActivityStatus(ids, newStatus);
        // The above implementation works but is not efficient. Instead of fetching each activity one by one, use a batch update query.
//        @PutMapping("activities/{newStatus}")
//        @Transactional
//        public void updateActivityStatus(Authentication authentication,
//                @RequestBody List<Long> ids,
//                @PathVariable String newStatus) {
//            var user = getUser(authentication);
//
//            // Check user access (if needed) before performing bulk update
//
//    }

        // Repository Query
//    @Modifying
//    @Query("UPDATE Activities a SET a.status = :status WHERE a.id IN :ids AND a.user.id = :userId")
//    void updateStatusByIds(@Param("ids") List<Long> ids, @Param("status") Status status, @Param("userId") Long userId);
    }


    @GetMapping("{userId}/weekly-activity-summary")
    public WeeklyActivitySummaryDTO getWeeklyActivitySummary(@PathVariable Long userId) {
        return activitiesService.weeklyActivitySummaryDTO(userId);
    }

    @PostMapping("activities/check-conflicts")
    public ActivitiesConflictDTO checkConflicts(@RequestBody List<ActivityRequest> request) {
        return activitiesService.checkConflicts(request);
    }


    private UserRegister getUser(Authentication authentication) {
        var userName = authentication.getName();
        var user = userRepository.findUserByUserName(userName);
        return user;
    }

}
