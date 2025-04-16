package com.fawry.order.sevices;

import org.springframework.http.ResponseEntity;

public interface StoreService {
    boolean checkAvailability(String productCode);

    ResponseEntity<String> consume(String product_code);
}