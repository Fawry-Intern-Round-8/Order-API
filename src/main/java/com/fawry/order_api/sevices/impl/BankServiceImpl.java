package com.fawry.order_api.sevices.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fawry.order_api.entity.DepositRequest;
import com.fawry.order_api.entity.WithdrawRequest;
import com.fawry.order_api.sevices.BankService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {
    private final RestTemplate restTemplate;
    private final String depositUrl = "http://localhost:8081/bank/transactions/deposit";
    private final String withdrawUrl = "http://localhost:8081/bank/transactions/withdraw";
    @Override
    public ResponseEntity<String> deposit(DepositRequest depositRequest) {
        return restTemplate.postForEntity(depositUrl, depositRequest, String.class);
    }

    @Override
    public ResponseEntity<String> withdraw(WithdrawRequest withdrawRequest) {
        return restTemplate.postForEntity(withdrawUrl, withdrawRequest, String.class);
    }
}