package com.personal.dailyActivities.dto;

import com.personal.dailyActivities.enums.Category;
import com.personal.dailyActivities.enums.Level;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HobbiesRequest {

    private String description;
    private Category category;
    private LocalDateTime startDate;
    private String progress; // send as JSON string
    private Level level;
}
