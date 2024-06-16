package com.greentechpay.bff.admin.dto.response;

import com.greentechpay.bff.admin.dto.Status;
import com.greentechpay.bff.admin.dto.TransferType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentHistoryDto {
    private BigDecimal amount;
    private String userId;
    private String vendorName;
    private String toUser;
    private String requestField;
    private String serviceName;
    private String merchantName;
    private String senderRequestId;
    private String senderIban;
    private String receiverIban;
    private TransferType transferType;
    private LocalDateTime paymentDate;
    private String transactionId;
    private Status status;
}
