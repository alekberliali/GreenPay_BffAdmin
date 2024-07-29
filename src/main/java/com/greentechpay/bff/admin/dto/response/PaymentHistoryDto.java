package com.greentechpay.bff.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greentechpay.bff.admin.dto.Currency;
import com.greentechpay.bff.admin.dto.Status;
import com.greentechpay.bff.admin.dto.TransferType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentHistoryDto {
    private Long id;
    private String receiptId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private LocalDateTime updateDate;
    private String senderRequestId;
    private String transactionId;
    private String externalPaymentId;
    private String serviceName;
    private String categoryName;
    private String senderPhoneNumber;
    private String requestField;
    private String senderIban;
    private String receiverIban;
    private String merchantName;
    private String vendorName;
    private Currency currency;
    private TransferType transferType;
    private Status status;
}
