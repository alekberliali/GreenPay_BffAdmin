package com.grenntechpay.bffadmin.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RequestResponsePaymentHistoryDto {
    private Long id;
    private String senderRequestId;
    private String userId;
    private BigDecimal amount;
    private String toUser;
    private String serviceName;
    private String transferType;
    private LocalDateTime paymentDate;
    private String status;
}
