package com.greentechpay.bff.admin.client.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StatisticCriteria {
    String userId;
    Integer serviceId;
    LocalDate startDate;
    LocalDate endDate;
}
