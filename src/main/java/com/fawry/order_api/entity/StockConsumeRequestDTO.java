package com.fawry.order_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockConsumeRequestDTO {
    private Long productId;
    private int quantity;
    private String customerEmail;
    private double longitude;
    private double latitude;
}