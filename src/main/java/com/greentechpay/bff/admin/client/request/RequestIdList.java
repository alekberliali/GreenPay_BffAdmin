package com.greentechpay.bff.admin.client.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class RequestIdList {
    private List<Integer> vendorIds;
    private List<Integer> serviceIds;
    private List<Long> merchantIds;
}
