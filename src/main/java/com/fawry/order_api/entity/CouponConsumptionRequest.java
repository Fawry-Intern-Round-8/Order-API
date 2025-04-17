package com.fawry.order_api.entity;

import lombok.Data;

@Data
public class CouponConsumptionRequest {
    private String code;
    private String orderId;
}