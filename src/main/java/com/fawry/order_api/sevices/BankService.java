package com.fawry.order_api.sevices;

import org.springframework.http.ResponseEntity;

import com.fawry.order_api.entity.DepositRequest;
import com.fawry.order_api.entity.WithdrawRequest;

public interface BankService {
    ResponseEntity<String> deposit( DepositRequest depositRequest);
    ResponseEntity<String> withdraw( WithdrawRequest withdrawRequest);
}