package com.personal.dailyActivities.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.personal.dailyActivities.auth.entity.UserRegister;
import com.personal.dailyActivities.enums.Category;
import com.personal.dailyActivities.enums.Level;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "hobbies")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Hobbies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;
    private LocalDateTime startDate;

    @Column(columnDefinition = "JSON")
    private String progress; // JSON field
    private Level level;
//    private Object photos; // JSON array of photo URLs related to the hobby.

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserRegister user;



//    Purpose: Track user hobbies, their progress, and achievements.
//            Fields:
//    id (Primary Key): Unique identifier for each hobby.
//    user_id (Foreign Key): Links to the users table.
//    name: Name of the hobby.
//    description: A short description.
//    category: Category of the hobby (e.g., Art, Music, Sports).
//    start_date: When the hobby was started.
//    progress: Progress percentage or milestones in JSON (e.g., { "milestone1": "completed", "milestone2": "in progress" }).
//    achievements: Achievements or certifications earned (JSON for flexibility).
//    level: Current level or proficiency (e.g., Beginner, Intermediate, Advanced).
//    photos (Optional): JSON array of photo URLs related to the hobby.
//            created_at, updated_at: Timestamps for auditing.
}
