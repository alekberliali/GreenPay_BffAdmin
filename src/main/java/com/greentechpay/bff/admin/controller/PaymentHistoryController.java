package com.greentechpay.bff.admin.controller;

import com.greentechpay.bff.admin.dto.Currency;
import com.greentechpay.bff.admin.dto.Status;
import com.greentechpay.bff.admin.dto.TransferType;
import com.greentechpay.bff.admin.dto.response.PaymentHistoryDto;
import com.greentechpay.bff.admin.service.PaymentHistoryService;
import com.greentechpay.bff.admin.dto.response.PageResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment-history")
@Validated
public class PaymentHistoryController {

    private final PaymentHistoryService paymentHistoryService;

    @GetMapping("/get-all")
    public ResponseEntity<PageResponse<List<PaymentHistoryDto>>>
    getAll(@RequestParam @Min(value = 0, message = "pages size can not be less than 0") Integer page,
           @RequestParam @Min(value = 0, message = "elements size can not be less than 0") Integer size) {
        return ResponseEntity.ok(paymentHistoryService.getAll(page, size));
    }

    @GetMapping("/get-by-user")
    public ResponseEntity<PageResponse<Map<LocalDate, List<PaymentHistoryDto>>>>
    getAllByUserId(@RequestParam @NotNull(message = "page can not be null") Integer page,
                   @RequestParam @Min(value = 0, message = "pages size can not be less than 0") Integer size,
                   @RequestParam @Min(value = 0, message = "elements size can not be less than 0") String userId) {
        return ResponseEntity.ok(paymentHistoryService.getAllWithPageByUserId(page, size, userId));
    }

    @GetMapping("/get-payment-history-filter")
    public ResponseEntity<PageResponse<List<PaymentHistoryDto>>>
    getAllWithPageByFilter(@RequestParam @Min(value = 0, message = "pages size can not be less than 0") Integer page,
                           @RequestParam @Min(value = 0, message = "elements size can not be less than 0") Integer size,
                           @RequestParam String userId,
                           @RequestParam @Past(message = "startDate must be a past date") LocalDate startDate,
                           @RequestParam @Past(message = "startDate must be a past date") LocalDate endDate,
                           @RequestParam String transactionId,
                           @RequestParam List<Currency> currencies,
                           @RequestParam List<TransferType> types,
                           @RequestParam List<Status> statuses) {
        return ResponseEntity.ok(paymentHistoryService.getAllWithPageByFilter(page, size, userId, startDate, endDate,
                transactionId, currencies, types, statuses));
    }
}
