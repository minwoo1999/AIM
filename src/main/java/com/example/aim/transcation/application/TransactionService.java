package com.example.aim.transcation.application;

import com.example.aim.transcation.application.dto.response.TransactionDepositResponseDto;
import com.example.aim.transcation.application.dto.response.TransactionHistoryListResponseDto;
import com.example.aim.transcation.application.dto.response.TransactionWithdrawResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    TransactionDepositResponseDto deposit(String username, BigDecimal amount);

    TransactionWithdrawResponseDto withdraw(String name, BigDecimal amount);

    List<TransactionHistoryListResponseDto> getTransactionHistory(String username);

}
