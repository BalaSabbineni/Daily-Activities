package com.personal.dailyActivities.dto;

import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Data
public class WeeklyActivitySummaryDTO {

    private String userName;
    private Summary summary;
    private Map<DayOfWeek, List<ActivityDetailsByDay>> activitiesByDay = new HashMap<>();
}
