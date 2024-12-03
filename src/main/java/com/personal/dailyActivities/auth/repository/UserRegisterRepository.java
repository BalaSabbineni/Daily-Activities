package com.personal.dailyActivities.auth.repository;

import com.personal.dailyActivities.auth.entity.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegisterRepository extends JpaRepository<UserRegister, Integer> {

    UserRegister findUserByUserName(String userName);
}
