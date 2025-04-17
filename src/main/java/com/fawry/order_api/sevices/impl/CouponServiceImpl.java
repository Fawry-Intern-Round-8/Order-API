package com.fawry.order_api.sevices.impl;

import com.fawry.order_api.entity.CouponConsumptionResponse;
import com.fawry.order_api.entity.CouponValidationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fawry.order_api.entity.CouponConsumptionRequest;
import com.fawry.order_api.sevices.CouponService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final RestTemplate restTemplate;
    private final String validateCouponUrl = "http://localhost:8082/api/v1/coupons/validate";
    private final String consumeCouponUrl = "http://localhost:8082/api/v1/coupons/transactions";
    @Override
    public Optional<CouponValidationResponse> validateCoupon(String couponCode) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(validateCouponUrl)
                    .queryParam("code", couponCode)
                    .toUriString();
            System.out.println(url);
            ResponseEntity<CouponValidationResponse> response = restTemplate.getForEntity(
                    url,
                    CouponValidationResponse.class
            );
            System.out.println(response.getBody());
            return Optional.ofNullable(response.getBody());
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to validate coupon", e);
        }
    }
    @Override
    public ResponseEntity<String> consume(CouponConsumptionRequest consumptionRequest) {
        return restTemplate.postForEntity(
            consumeCouponUrl, 
            consumptionRequest,
            String.class
        );
    }
}