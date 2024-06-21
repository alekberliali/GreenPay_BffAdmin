package com.greentechpay.bff.admin.client.response;

import com.greentechpay.bff.admin.dto.Currency;
import com.greentechpay.bff.admin.dto.Status;
import com.greentechpay.bff.admin.dto.TransferType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentHistory {
    private Long id;
    private BigDecimal amount;
    private String userId;
    private Integer vendorId;
    private String toUser;
    private String externalPaymentId;
    private String requestField;
    private Integer serviceId;
    private Integer merchantId;
    private String senderRequestId;
    private String senderIban;
    private String receiverIban;
    private TransferType transferType;
    private LocalDateTime paymentDate;
    private Currency currency;
    private String transactionId;
    private Status status;
}
