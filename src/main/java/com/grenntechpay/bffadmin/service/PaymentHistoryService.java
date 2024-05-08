package com.grenntechpay.bffadmin.service;

import com.grenntechpay.bffadmin.client.PaymentHistoryClient;
import com.grenntechpay.bffadmin.dto.request.PageRequestDto;
import com.grenntechpay.bffadmin.dto.response.PageResponse;
import com.grenntechpay.bffadmin.dto.response.ResponsePaymentHistoryDto;
import com.grenntechpay.bffadmin.mapper.PaymentHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService {

    private final PaymentHistoryClient paymentHistoryClient;
    private final PaymentHistoryMapper paymentHistoryMapper;

    public PageResponse<List<ResponsePaymentHistoryDto>> getAll(Integer page, Integer size) {
        PageRequestDto pageRequestDto = new PageRequestDto(page, size);
        var response = Objects.requireNonNull(paymentHistoryClient.getAll(pageRequestDto).getBody());
        var content = response.getContent()
                .stream()
                .map(paymentHistoryMapper::requestToResponse)
                .toList();
        return PageResponse.<List<ResponsePaymentHistoryDto>>builder()
                .totalElements(response.getTotalElements())
                .totalPages(response.getTotalPages())
                .content(content)
                .build();
    }
}
