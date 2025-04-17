package com.fawry.order_api.entity;

import lombok.Data;

@Data
public class CouponValidationResponse {
    private boolean isValid;
    private int value;
    private String discountType;
}
