package com.grenntechpay.bffadmin.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletLimitDto {
    private String userId;
    private BigDecimal limitBalance;
}
