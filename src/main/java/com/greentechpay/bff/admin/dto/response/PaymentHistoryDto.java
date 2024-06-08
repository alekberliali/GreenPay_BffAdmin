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
    private Long id;
    private String senderRequestId;
    private String vendorName;
    private String userId;
    private BigDecimal amount;
    private String toUser;
    private String requestField;
    private String serviceName;
    private TransferType transferType;
    private LocalDateTime paymentDate;
    private Status status;
}