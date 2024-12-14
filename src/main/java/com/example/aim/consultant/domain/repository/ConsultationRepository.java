package com.example.aim.consultant.domain.repository;

import com.example.aim.consultant.domain.ConsultationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<ConsultationEntity, Long> {
}
