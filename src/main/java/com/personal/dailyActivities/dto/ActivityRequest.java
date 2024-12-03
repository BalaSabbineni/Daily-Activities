package com.personal.dailyActivities.dto;

import com.personal.dailyActivities.enums.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ActivityRequest {

    private String name;
    private String description;
    private Category category;
    private Status status;
    private Priority priority;
    private TimeOfDay timeOfDay;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isRecurring;
    private RecurrencePattren recurrencePattern;
}
