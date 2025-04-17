package com.fawry.order_api.sevices;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.fawry.order_api.entity.StockConsumeRequestDTO;

public interface StoreService {
    boolean checkProductAvailability(Long productId,int quantity);
    ResponseEntity<String>consumeStock(StockConsumeRequestDTO stockConsumeRequestDTO);
}