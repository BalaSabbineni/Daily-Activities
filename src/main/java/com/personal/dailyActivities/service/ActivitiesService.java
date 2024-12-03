package com.personal.dailyActivities.service;

import com.personal.dailyActivities.auth.entity.UserRegister;
import com.personal.dailyActivities.dto.ActivitiesConflictDTO;
import com.personal.dailyActivities.dto.ActivityRequest;
import com.personal.dailyActivities.dto.WeeklyActivitySummaryDTO;
import com.personal.dailyActivities.entity.Activities;
import com.personal.dailyActivities.enums.Category;
import com.personal.dailyActivities.enums.Priority;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ActivitiesService {

    Object createActivities(UserRegister user, List<ActivityRequest> request);

    Object getActivitiesByTimeOfDay(Long userId);

    Object getActivityBySearch(Category category, Priority priority);

    List<Activities> updateActivityStatus(List<Integer> ids, String newStatus);

    WeeklyActivitySummaryDTO weeklyActivitySummaryDTO(Long userId);

    ActivitiesConflictDTO checkConflicts(List<ActivityRequest> request);
}
