package com.example.aim.security.domain.repository;

import com.example.aim.security.domain.SecurityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityRepository extends JpaRepository<SecurityEntity, Long> {

    Optional<SecurityEntity> findByCode(String securityCode);

}