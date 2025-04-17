package com.fawry.order_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CouponConsumptionResponse {
    private Long id;
    private Long couponId;
    private String orderId;
    private LocalDate consumedAt;
}
