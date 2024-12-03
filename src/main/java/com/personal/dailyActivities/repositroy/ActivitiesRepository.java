package com.personal.dailyActivities.repositroy;

import com.personal.dailyActivities.entity.Activities;
import com.personal.dailyActivities.enums.Category;
import com.personal.dailyActivities.enums.Priority;
import com.personal.dailyActivities.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivitiesRepository extends JpaRepository<Activities, Long> {

    List<Activities> findActivitiesByUserId(Long userId);

    List<Activities> findActivitiesByCategory(Category category);

    List<Activities> findActivitiesByCategoryAndPriority(Category category, Priority priority);

    @Modifying
    @Query("UPDATE Activities a SET a.status = :status WHERE a.id IN :ids AND a.user.id = :userId")
    void updateStatusByIds(@Param("ids") List<Long> ids, @Param("status") Status status, @Param("userId") Long userId);
}
