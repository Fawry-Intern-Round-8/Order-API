package com.fawry.order.sevices.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fawry.order.sevices.StoreService;

@Service
public class StoreServiceImpl implements StoreService{
    public StoreServiceImpl() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean checkAvailability(String productCode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkAvailability'");
    }

    @Override
    public ResponseEntity<String> consume(String product_code) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consume'");
    }
}