package com.personal.dailyActivities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class DailyActivitiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyActivitiesApplication.class, args);
	}

}
