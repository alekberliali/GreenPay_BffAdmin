package com.greentechpay.bff.admin.dto.request;

import com.greentechpay.bff.admin.dto.Status;
import com.greentechpay.bff.admin.dto.TransferType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentHistory {
    private Long id;
    private String senderRequestId;
    private String userId;
    private BigDecimal amount;
    private String toUser;
    private Integer vendorId;
    private String requestField;
    private Integer serviceId;
    private TransferType transferType;
    private LocalDateTime paymentDate;
    private Status status;
}
