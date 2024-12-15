package com.example.aim.portfolio.domain;

import com.example.aim.consultant.domain.ConsultationEntity;
import com.example.aim.security.domain.SecurityEntity;
import com.example.aim.shared.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "Portfolios")
@AllArgsConstructor
@Builder
public class PortfolioEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id", nullable = false)
    private ConsultationEntity consultation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "security_id", nullable = false)
    private SecurityEntity security;

    @Column(nullable = false)
    private Integer quantity;

}
