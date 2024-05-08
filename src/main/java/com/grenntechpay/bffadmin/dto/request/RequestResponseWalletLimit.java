package com.grenntechpay.bffadmin.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestResponseWalletLimit {
    private String phoneNumber;
    private BigDecimal amountLimit;
}
