package com.grenntechpay.bffadmin.client;

import com.grenntechpay.bffadmin.dto.request.PageRequestDto;
import com.grenntechpay.bffadmin.dto.request.RequestResponsePaymentHistoryDto;
import com.grenntechpay.bffadmin.dto.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "paymentHistory", url = "${app.feign.payment-history.config.url}")
public interface PaymentHistoryClient {

    @PostMapping("/all")
    ResponseEntity<PageResponse<List<RequestResponsePaymentHistoryDto>>> getAll(PageRequestDto pageRequestDto);
}
