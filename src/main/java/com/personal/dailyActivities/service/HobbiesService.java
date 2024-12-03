package com.personal.dailyActivities.service;

import com.personal.dailyActivities.dto.MonthlyHobbyProgressDTO;
import com.personal.dailyActivities.entity.Hobbies;

import java.util.List;

public interface HobbiesService {

    MonthlyHobbyProgressDTO monthlyHobbyProgress(String userName, String month, List<Hobbies> hobbiesList);
}
