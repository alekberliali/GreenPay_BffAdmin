package com.greentechpay.bff.admin.client.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class StatisticCriteria {
    List<Integer> serviceIdList;
    String userId;
    Integer vendorId;
    String categoryName;
    LocalDate startDate;
    LocalDate endDate;
}
