package com.grenntechpay.bffadmin.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ResponsePaymentHistoryDto {
    private String senderRequestId;
    private String userId;
    private BigDecimal amount;
    private String toUser;
    private String serviceName;
    private String transferType;
    private LocalDateTime paymentDate;
    private String status;
}
