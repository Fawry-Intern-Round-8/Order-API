package com.fawry.order_api.sevices;

import org.springframework.http.ResponseEntity;

public interface StoreService {
    boolean checkAvailability(String productCode);
    ResponseEntity<String>consume(String product_code);
}