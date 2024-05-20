package com.greentechpay.bff.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletLimitDto {
    @NotBlank(message = "user id can not be empty")
    private String userId;
    private BigDecimal limitBalance;
}
