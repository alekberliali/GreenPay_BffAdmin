package com.grenntechpay.bffadmin.dto.request;

import lombok.Data;

@Data
public class RequestResponseWalletStatus {
    private String walletId;
    private Boolean status;
}
