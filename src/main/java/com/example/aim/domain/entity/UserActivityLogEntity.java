package com.example.aim.domain.entity;

import com.example.aim.domain.ActivityType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "UserActivityLogs")
public class UserActivityLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ActivityType activityType;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime activityTimestamp;


}
