package com.example.aim.member.domain.repository;

import com.example.aim.member.domain.UserActivityLogEntity;
import com.example.aim.member.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityLogRepository extends JpaRepository<UserActivityLogEntity, Long> {

}
