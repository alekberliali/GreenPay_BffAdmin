package com.greentechpay.bff.admin.client.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StatisticCriteria {
    String userId;
    LocalDate startDate;
    LocalDate endDate;
}
