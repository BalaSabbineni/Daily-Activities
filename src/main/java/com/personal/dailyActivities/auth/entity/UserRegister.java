package com.personal.dailyActivities.auth.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_register" )
@Data
public class UserRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String passWord;
}
