package com.fawry.order_api.sevices;

import com.fawry.order_api.entity.CouponValidationResponse;
import org.springframework.http.ResponseEntity;

import com.fawry.order_api.entity.CouponConsumptionRequest;

import java.util.Optional;

public interface CouponService {
    Optional<CouponValidationResponse> validateCoupon(String couponCode);
    ResponseEntity<String> consume(CouponConsumptionRequest consumptionRequest);
}