package com.personal.dailyActivities.controller;

import com.personal.dailyActivities.dto.YearlyAchievementReportDTO;
import com.personal.dailyActivities.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userId}/yearly-report")
    public YearlyAchievementReportDTO getYearlyAchievementReport(@RequestParam int year, @PathVariable Long userId) {
        return userService.getYearlyAchievementReport(year, userId);
    }
}
