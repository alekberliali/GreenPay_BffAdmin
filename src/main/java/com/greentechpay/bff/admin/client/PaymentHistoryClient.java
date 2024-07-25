package com.greentechpay.bff.admin.client;

import com.greentechpay.bff.admin.client.request.FilterDto;
import com.greentechpay.bff.admin.client.request.RequestDto;
import com.greentechpay.bff.admin.client.request.StatisticCriteria;
import com.greentechpay.bff.admin.client.response.PaymentHistory;
import com.greentechpay.bff.admin.dto.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@FeignClient(value = "paymentHistory", url = "${app.feign.payment-history.config.url}")
public interface PaymentHistoryClient {
    @PostMapping(value = "/payment-history/filter")
    PageResponse<List<PaymentHistory>> getAllWithPageByFilter(@RequestBody RequestDto<FilterDto> requestDto);

    @GetMapping(value = "/payment-history/{id}")
    PaymentHistory getById(@PathVariable Long id, @RequestParam(value = "merchantId") @Nullable Long merchantId);

    @PostMapping(value = "/payment-history/category-statistics")
    ResponseEntity<Map<String, BigDecimal>> getCategoryStatistics(StatisticCriteria statisticCriteria);

    @PostMapping(value = "/payment-history/service-statistics")
    ResponseEntity<Map<Integer, BigDecimal>> getServiceStatics(StatisticCriteria statisticCriteria);

    @PostMapping(value = "/payment-history/merchant-statistics")
    ResponseEntity<Map<Long, BigDecimal>> getMerchantStatistics(StatisticCriteria statisticCriteria);

    @PostMapping(value = "/payment-history/category-statistics-by-name")
    ResponseEntity<Map<LocalDate, BigDecimal>> getCategoryStatisticsByName(StatisticCriteria statisticCriteria);
}