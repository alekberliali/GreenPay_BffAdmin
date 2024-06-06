package com.greentechpay.bff.admin.client;

import com.greentechpay.bff.admin.client.request.FilterDto;
import com.greentechpay.bff.admin.client.request.RequestDto;
import com.greentechpay.bff.admin.client.request.StatisticCriteria;
import com.greentechpay.bff.admin.dto.request.PaymentHistory;
import com.greentechpay.bff.admin.dto.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@FeignClient(value = "paymentHistory", url = "${app.feign.payment-history.config.url}")
public interface PaymentHistoryClient {
    @PostMapping(value = "/payment-history/filter")
    ResponseEntity<PageResponse<List<PaymentHistory>>> getAllWithPageByFilter(RequestDto<FilterDto> requestDto);

    @PostMapping(value = "/payment-history/category-statistics")
    ResponseEntity<Map<String, BigDecimal>> getCategoryStatistics(StatisticCriteria statisticCriteria);

    @PostMapping(value = "/payment-history/service-statistics")
    ResponseEntity<PageResponse<Map<Integer, BigDecimal>>> getServiceStatics(RequestDto<StatisticCriteria> requestDto);
}