package com.example.aim.transcation.application.impl;

import com.example.aim.member.application.MemberService;
import com.example.aim.member.domain.UserEntity;
import com.example.aim.member.domain.repository.MemberRepository;
import com.example.aim.transcation.application.TransactionService;
import com.example.aim.transcation.application.dto.response.TransactionDepositResponseDto;
import com.example.aim.transcation.application.dto.response.TransactionHistoryListResponseDto;
import com.example.aim.transcation.application.dto.response.TransactionWithdrawResponseDto;
import com.example.aim.transcation.domain.TransactionEntity;
import com.example.aim.transcation.domain.TransactionType;
import com.example.aim.transcation.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public TransactionDepositResponseDto deposit(String username, BigDecimal amount) {
        UserEntity user = memberRepository.findByUsernameWithLock(username)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        user.deposit(amount);
        saveTransaction(user, amount, TransactionType.DEPOSIT);

        return TransactionDepositResponseDto.builder()
                .amount(amount)
                .build();
    }

    @Transactional
    public TransactionWithdrawResponseDto withdraw(String username, BigDecimal amount) {
        UserEntity user = memberRepository.findByUsernameWithLock(username)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        user.withdraw(amount);
        saveTransaction(user, amount, TransactionType.WITHDRAW);

        return TransactionWithdrawResponseDto.builder()
                .amount(amount)
                .build();
    }

    public List<TransactionHistoryListResponseDto> getTransacti onHistory(String username) {
        UserEntity user = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        List<TransactionEntity> transactions = transactionRepository.findByUser(user);

        return transactions.stream()
                .map(transaction -> TransactionHistoryListResponseDto.builder()
                        .transactionId(transaction.getId())
                        .transactionType(transaction.getTransactionType())
                        .amount(transaction.getAmount())
                        .createdAt(transaction.getCreatedAt())
                        .username(user.getUsername())
                        .build())
                .collect(Collectors.toList());
    }

    private void saveTransaction(UserEntity user, BigDecimal amount, TransactionType transactionType) {
        TransactionEntity transaction = TransactionEntity.builder()
                .user(user)
                .amount(amount)
                .transactionType(transactionType)
                .build();
        transactionRepository.save(transaction);
    }
}
