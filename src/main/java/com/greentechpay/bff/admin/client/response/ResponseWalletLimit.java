package com.greentechpay.bff.admin.client.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ResponseWalletLimit {
    private String phoneNumber;
    private BigDecimal amountLimit;
}
