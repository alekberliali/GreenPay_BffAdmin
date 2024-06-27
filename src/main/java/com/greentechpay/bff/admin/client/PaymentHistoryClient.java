package com.greentechpay.bff.admin.client;

import com.greentechpay.bff.admin.client.request.FilterDto;
import com.greentechpay.bff.admin.client.request.RequestDto;
import com.greentechpay.bff.admin.client.request.StatisticCriteria;
import com.greentechpay.bff.admin.client.response.PaymentHistory;
import com.greentechpay.bff.admin.dto.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@FeignClient(value = "paymentHistory", url = "${app.feign.payment-history.config.url}")
public interface PaymentHistoryClient {
    @PostMapping(value = "/payment-history/filter")
    ResponseEntity<PageResponse<List<PaymentHistory>>> getAllWithPageByFilter(RequestDto<FilterDto> requestDto);

    @PostMapping(value = "/payment-history/category-statistics")
    ResponseEntity<Map<String, BigDecimal>> getCategoryStatistics(StatisticCriteria statisticCriteria);

    @PostMapping(value = "/payment-history/service-statistics")
    ResponseEntity<Map<Integer, BigDecimal>> getServiceStatics(StatisticCriteria statisticCriteria);

    @PostMapping(value = "/payment-history/merchant-statistics")
    ResponseEntity<Map<Integer, BigDecimal>> getMerchantStatistics(StatisticCriteria statisticCriteria);

    @PostMapping(value = "/payment-history/category-statistics-by-name")
    ResponseEntity<Map<LocalDate, BigDecimal>> getCategoryStatisticsByName(StatisticCriteria statisticCriteria);
}