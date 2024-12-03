package com.personal.dailyActivities.dto;

import com.personal.dailyActivities.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class YearlyAchievementReportDTO {

    private String userName;
    private int year;
    private int totalActivities;
    private int completedActivities;
    private List<String> hobbiesMastered;
    private Map<Category, String> timeSpentSummary = new HashMap<>();
}
