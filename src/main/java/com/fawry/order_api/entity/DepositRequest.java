package com.fawry.order_api.entity;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositRequest {
    @NotBlank
    @Pattern(regexp = "^[0-9]{16}$")
    private String cardNumber;
    private double amount;
    private String notes;
}