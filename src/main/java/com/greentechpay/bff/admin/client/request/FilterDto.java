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
    private Integer vendorId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String transactionId;
    private List<Currency> currencies;
    private List<TransferType> types;
    private List<Status> statuses;
}
