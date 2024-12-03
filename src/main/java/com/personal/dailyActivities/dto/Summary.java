package com.personal.dailyActivities.dto;

import com.personal.dailyActivities.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Summary {

    private int totalActivities;
    private int completedActivities;
    private DayOfWeek mostActiveDay;
    private Map<Category, CategorySummaryByType> categorySummary = new HashMap<>();
}
