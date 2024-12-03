package com.personal.dailyActivities.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.personal.dailyActivities.auth.entity.UserRegister;
import com.personal.dailyActivities.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Activities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private TimeOfDay timeOfDay;
    /*
    * Format: yyyy-MM-dd'T'HH:mm:ss
    Here's a breakdown of the format:
    yyyy: Four-digit year
    MM: Two-digit month (01-12)
    dd: Two-digit day of the month (01-31)
    T: Literal character to separate date and time
    HH: Two-digit hour (00-23)
    mm: Two-digit minute (00-59)
    ss: Two-digit second (00-59)
     */
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    //private Location location;
    private Boolean isRecurring;
    @Enumerated(EnumType.STRING)
    private RecurrencePattren recurrencePattern;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserRegister user;


//    id (Primary Key): Unique identifier for each activity.
//    user_id (Foreign Key): Links to the users table.
//    name: Name of the activity.
//    description: Detailed description of the activity.
//    category: Category of the activity (e.g., Work, Health, Leisure).
//    status: Current status (e.g., Pending, In Progress, Completed).
//    priority: Priority level (e.g., Low, Medium, High).
//    start_time, end_time: Timestamp for tracking duration.
//    location (Optional): JSON field for location details (latitude, longitude, or address).
//    is_recurring: Boolean flag for recurring activities.
//    recurrence_pattern (Optional): Stores recurrence rules (e.g., daily, weekly).
//    created_at, updated_at: Timestamps for auditing.
    //  time_of_day ENUM('Morning', 'Afternoon', 'Evening', 'Night') NOT NULL,
}
