package com.greentechpay.bff.admin.client;

import com.greentechpay.bff.admin.client.request.FilterDto;
import com.greentechpay.bff.admin.client.request.PageRequestDto;
import com.greentechpay.bff.admin.dto.response.PageResponse;
import com.greentechpay.bff.admin.dto.response.PaymentHistoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@FeignClient(value = "paymentHistory", url = "${app.feign.payment-history.config.url}")
public interface PaymentHistoryClient {

    @PostMapping("/payment-history/all")
    ResponseEntity<PageResponse<List<PaymentHistoryDto>>> getAll(PageRequestDto pageRequestDto);

    @PostMapping("/payment-history/page/{userId}")
    ResponseEntity<PageResponse<Map<LocalDate, List<PaymentHistoryDto>>>>
    getAllWithPageByUserId(@PathVariable String userId, PageRequestDto pageRequestDto);

    @PostMapping(value = "/payment-history-filter")
    ResponseEntity<PageResponse<List<PaymentHistoryDto>>> getAllWithPageByFilter(FilterDto filterDto);
}