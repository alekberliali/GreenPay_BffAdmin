package com.greentechpay.bff.admin.dto;

public enum TransferType {
    IbanToPhoneNumber,
    IbanToIban,
    BalanceToCard,
    CardToBalance,
    BillingPayment,
    Qr,
    Nfc,
}
