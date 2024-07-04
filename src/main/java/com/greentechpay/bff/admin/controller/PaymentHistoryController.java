package com.greentechpay.bff.admin.controller;

import com.greentechpay.bff.admin.dto.Currency;
import com.greentechpay.bff.admin.dto.Status;
import com.greentechpay.bff.admin.dto.TransferType;
import com.greentechpay.bff.admin.dto.response.PageResponse;
import com.greentechpay.bff.admin.dto.response.PaymentHistoryDto;
import com.greentechpay.bff.admin.service.PaymentHistoryService;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment-history")
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PaymentHistoryController {

    private final PaymentHistoryService paymentHistoryService;

    @GetMapping("/get-payment-history-filter")
    public ResponseEntity<PageResponse<List<PaymentHistoryDto>>>
    getAllWithPageByFilter(@RequestHeader(value = "agent-name") String agentName,
                           @RequestHeader(value = "agent-password") String agentPassword,
                           @RequestHeader(value = "agent-id") String agentId,
                           @RequestHeader(value = "access-token") String accessToken,
                           @RequestParam @Min(value = 0, message = "pages size can not be less than 0") Integer page,
                           @RequestParam @Min(value = 0, message = "elements size can not be less than 0") Integer size,
                           @RequestParam @Nullable String userId,
                           @RequestParam @Nullable @Past(message = "startDate must be a past date") LocalDate startDate,
                           @RequestParam @Nullable @Past(message = "startDate must be a past date") LocalDate endDate,
                           @RequestParam @Nullable String transactionId,
                           @RequestParam @Nullable Integer vendorId,
                           @RequestParam @Nullable Long merchantId,
                           @RequestParam @Nullable List<Currency> currencies,
                           @RequestParam @Nullable List<TransferType> types,
                           @RequestParam @Nullable List<Status> statuses) {
        return ResponseEntity.ok(paymentHistoryService.getAllWithPageByFilter(agentName, agentPassword, agentId,
                accessToken, page, size, userId, vendorId, merchantId, startDate, endDate, transactionId, currencies,
                types, statuses));

    }
    //TODO add currency or iban
    @GetMapping("/category-statistics")
    public ResponseEntity<Map<String, BigDecimal>> getCategoryStatistics(@RequestParam @Nullable String userId,
                                                                         @RequestParam @Nullable LocalDate startDate,
                                                                         @RequestParam @Nullable LocalDate endDate) {
        return ResponseEntity.ok(paymentHistoryService.getCategoryStatistics(userId, startDate, endDate));
    }

    @GetMapping("/service-statistics")
    public ResponseEntity<Map<String, BigDecimal>>
    getServiceStatus(@RequestHeader(value = "agent-name") String agentName,
                     @RequestHeader(value = "agent-password") String agentPassword,
                     @RequestHeader(value = "agent-id") String agentId,
                     @RequestHeader(value = "access-token") String accessToken,
                     @RequestParam @NotNull List<Integer> serviceIdList,
                     @RequestParam @Nullable Integer vendorId,
                     @RequestParam @Nullable LocalDate startDate,
                     @RequestParam @Nullable LocalDate endDate) {
        return ResponseEntity.ok(paymentHistoryService.getServiceStatics(agentName, agentPassword, agentId,
                accessToken, serviceIdList, vendorId, startDate, endDate));
    }

    @GetMapping("/merchant-statistics")
    public ResponseEntity<Map<String, BigDecimal>>
    getMerchantStatistics(@RequestHeader(value = "agent-name") String agentName,
                          @RequestHeader(value = "agent-password") String agentPassword,
                          @RequestHeader(value = "agent-id") String agentId,
                          @RequestHeader(value = "access-token") String accessToken,
                          @RequestParam @Nullable LocalDate startDate,
                          @RequestParam @Nullable LocalDate endDate) {
        return ResponseEntity.ok(paymentHistoryService.getMerchantStatistics(agentName, agentPassword,
                agentId, accessToken, startDate, endDate));
    }

    @GetMapping("category-statistics-by-name")
    public ResponseEntity<Map<LocalDate, BigDecimal>>
    getCategoryStatisticsByCategoryName(@RequestParam @NotNull String categoryName,
                                        @RequestParam @Nullable Integer vendorId,
                                        @RequestParam @Nullable LocalDate startDate,
                                        @RequestParam @Nullable LocalDate endDate) {
        return ResponseEntity.ok(paymentHistoryService
                .getCategoryStatisticsByCategoryName(categoryName, vendorId, startDate, endDate));
    }
}
