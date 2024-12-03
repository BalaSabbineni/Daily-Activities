package com.personal.dailyActivities.dto;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyHobbyProgressDTO {

    private String username;
    private String month;
    private List<HobbyProgress> hobbyProgress;

    @Data
    public static class HobbyProgress {

        private String name;
        private int milestonesCompleted;
        private int pendingMilestones;
        private int progressPercentage;
    }

}
