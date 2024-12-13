package com.example.aim.transcation.domain.repository;

import com.example.aim.member.domain.UserEntity;
import com.example.aim.transcation.domain.TransactionEntity;
import jakarta.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByUser(UserEntity user);
}
