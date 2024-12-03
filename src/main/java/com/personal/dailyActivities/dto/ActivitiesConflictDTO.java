package com.personal.dailyActivities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivitiesConflictDTO {

    private List<ConflictsDTO> conflicts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConflictsDTO {

        private String activity1;
        private String activity2;
        private String conflictType;
        private String suggestedResolution;
    }
}
