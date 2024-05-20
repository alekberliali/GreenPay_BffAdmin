package com.greentechpay.bff.admin.service;

import com.greentechpay.bff.admin.client.request.FilterDto;
import com.greentechpay.bff.admin.client.request.PageRequestDto;
import com.greentechpay.bff.admin.client.PaymentHistoryClient;
import com.greentechpay.bff.admin.dto.Currency;
import com.greentechpay.bff.admin.dto.Status;
import com.greentechpay.bff.admin.dto.TransferType;
import com.greentechpay.bff.admin.dto.response.PageResponse;
import com.greentechpay.bff.admin.dto.response.PaymentHistoryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService {

    private final PaymentHistoryClient paymentHistoryClient;

    public PageResponse<List<PaymentHistoryDto>> getAll(Integer page, Integer size) {
        PageRequestDto pageRequestDto = new PageRequestDto(page, size);
        var response = Objects.requireNonNull(paymentHistoryClient.getAll(pageRequestDto).getBody());
        var content = response.getContent();

        return PageResponse.<List<PaymentHistoryDto>>builder()
                .totalElements(response.getTotalElements())
                .totalPages(response.getTotalPages())
                .content(content)
                .build();
    }

    public PageResponse<Map<LocalDate, List<PaymentHistoryDto>>> getAllWithPageByUserId(Integer page, Integer size,
                                                                                        String userId) {
        PageRequestDto pageRequestDto = new PageRequestDto(page, size);
        var response = Objects.requireNonNull(paymentHistoryClient.getAllWithPageByUserId(userId, pageRequestDto).getBody());
        return PageResponse.<Map<LocalDate, List<PaymentHistoryDto>>>builder()
                .totalPages(response.getTotalPages())
                .totalElements(response.getTotalElements())
                .content(response.getContent())
                .build();
    }

    public PageResponse<List<PaymentHistoryDto>>
    getAllWithPageByFilter(Integer page, Integer size, String userId, LocalDate startDate, LocalDate endDate,
                           String transactionId, List<Currency> currencies, List<TransferType> types, List<Status> statuses) {
        @Valid PageRequestDto pageRequestDto = new PageRequestDto(page, size);
        FilterDto filterDto = FilterDto.builder()
                .pageRequestDto(pageRequestDto)
                .userId(userId)
                .startDate(startDate)
                .endDate(endDate)
                .transactionId(transactionId)
                .currencies(currencies)
                .types(types)
                .statuses(statuses)
                .build();

        var response = Objects.requireNonNull(paymentHistoryClient.getAllWithPageByFilter(filterDto).getBody());

        return PageResponse.<List<PaymentHistoryDto>>builder()
                .totalPages(response.getTotalPages())
                .totalElements(response.getTotalElements())
                .content(response.getContent())
                .build();
    }
}
