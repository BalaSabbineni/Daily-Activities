package com.personal.dailyActivities.repositroy;

import com.personal.dailyActivities.entity.Hobbies;
import com.personal.dailyActivities.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HobbiesRepository extends JpaRepository<Hobbies, Long> {

    List<Hobbies> findHobbiesByCategory(Category category);
    List<Hobbies> findHobbiesByUserId(Long userId);
 }
