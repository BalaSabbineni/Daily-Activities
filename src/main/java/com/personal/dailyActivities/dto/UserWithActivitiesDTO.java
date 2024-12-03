package com.personal.dailyActivities.dto;

import com.personal.dailyActivities.entity.Activities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithActivitiesDTO {
    private String userName;
    private List<Activities> activities;
}
