package com.grenntechpay.bffadmin.controller;

import com.grenntechpay.bffadmin.dto.response.PageResponse;
import com.grenntechpay.bffadmin.dto.response.ResponsePaymentHistoryDto;
import com.grenntechpay.bffadmin.service.PaymentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment-history")
public class PaymentHistoryController {

    private final PaymentHistoryService paymentHistoryService;

    @GetMapping("/all")
    public ResponseEntity<PageResponse<List<ResponsePaymentHistoryDto>>> getAll(@RequestParam Integer page,
                                                                                @RequestParam Integer size) {
        return ResponseEntity.ok(paymentHistoryService.getAll(page, size));
    }
}
