package com.personal.dailyActivities.service.impl;

import com.personal.dailyActivities.dto.MonthlyHobbyProgressDTO;
import com.personal.dailyActivities.entity.Hobbies;
import com.personal.dailyActivities.service.HobbiesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class HobbiesServiceImpl implements HobbiesService {

    @Override
    public MonthlyHobbyProgressDTO monthlyHobbyProgress(String userName, String month, List<Hobbies> hobbiesList) {
        MonthlyHobbyProgressDTO dto = new MonthlyHobbyProgressDTO();

        dto.setUsername(userName);
        dto.setMonth(month);

        List<MonthlyHobbyProgressDTO.HobbyProgress> hobbyProgresses = hobbiesList.stream()
                .filter(h -> !Objects.isNull(h.getStartDate()))
                .filter(h -> Objects.equals(h.getStartDate().getMonth().name(), month.toUpperCase()))
                .map(hobbies -> {
                    MonthlyHobbyProgressDTO.HobbyProgress hobPro = new MonthlyHobbyProgressDTO.HobbyProgress();
                    hobPro.setName(hobbies.getCategory().name());
                    //TODO, We will get milestones data from JSON string progress, Need to implement it
                    /*
                    hobPro.setMilestonesCompleted();
                    hobPro.setPendingMilestones();
                    hobPro.setProgressPercentage();
                     */
                    return hobPro;
                }).toList();

        dto.setHobbyProgress(hobbyProgresses);

        return dto;
    }
}
