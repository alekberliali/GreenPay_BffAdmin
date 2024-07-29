package com.greentechpay.bff.admin.client.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greentechpay.bff.admin.dto.Currency;
import com.greentechpay.bff.admin.dto.Status;
import com.greentechpay.bff.admin.dto.TransferType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterDto {
    private String userId;
    private String receiptId;
    private Integer vendorId;
    private Long merchantId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String transactionId;
    private String categoryName;
    private List<Integer> serviceIdList;
    private List<Currency> currencies;
    private List<TransferType> transferTypes;
    private List<Status> statuses;
}
