package com.personal.dailyActivities.service;

import com.personal.dailyActivities.dto.YearlyAchievementReportDTO;

public interface UserService {

    YearlyAchievementReportDTO getYearlyAchievementReport(int year, Long userId);
}
