package com.fawry.order_api.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long productId;
    
    @Email
    private String customerEmail;
    
    @Positive
    private double price;
    
    @Pattern(regexp = "^[0-9]{16}$")
    private String cardNumber;
    
    private String couponCode;

    private int quantity;

    private double longitude;
    
    private double latitude; 
}