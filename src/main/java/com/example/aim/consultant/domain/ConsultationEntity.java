package com.example.aim.consultant.domain;

import com.example.aim.member.domain.UserEntity;
import com.example.aim.portfolio.domain.PortfolioType;
import com.example.aim.shared.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "Consultations")
public class ConsultationEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private PortfolioType portfolioType;



}