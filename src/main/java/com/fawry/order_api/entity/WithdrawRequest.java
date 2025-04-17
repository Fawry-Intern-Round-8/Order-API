package com.fawry.order_api.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
public class WithdrawRequest {
    @NotBlank
    @Pattern(regexp = "^[0-9]{16}$") 
    private String cardNumber;
    private double amount;
    private String notes;
}