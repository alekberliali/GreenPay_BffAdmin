package com.greentechpay.bff.admin.client.response;

import lombok.Data;

@Data
public class ResponseWalletStatus {
    private String walletId;
    private Boolean status;
}
