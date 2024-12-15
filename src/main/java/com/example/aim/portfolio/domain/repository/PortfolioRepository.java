package com.example.aim.portfolio.domain.repository;

import com.example.aim.portfolio.domain.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {
}