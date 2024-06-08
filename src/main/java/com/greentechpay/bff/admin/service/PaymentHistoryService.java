package com.greentechpay.bff.admin.service;

import com.greentechpay.bff.admin.client.ServiceClient;
import com.greentechpay.bff.admin.client.request.FilterDto;
import com.greentechpay.bff.admin.client.request.PageRequestDto;
import com.greentechpay.bff.admin.client.PaymentHistoryClient;
import com.greentechpay.bff.admin.client.request.RequestDto;
import com.greentechpay.bff.admin.client.request.StatisticCriteria;
import com.greentechpay.bff.admin.dto.Currency;
import com.greentechpay.bff.admin.dto.Status;
import com.greentechpay.bff.admin.dto.TransferType;
import com.greentechpay.bff.admin.dto.request.PaymentHistory;
import com.greentechpay.bff.admin.dto.response.PageResponse;
import com.greentechpay.bff.admin.dto.response.PaymentHistoryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService {

    private final PaymentHistoryClient paymentHistoryClient;
    private final ServiceClient serviceClient;

    public PageResponse<List<PaymentHistoryDto>>
    getAllWithPageByFilter(Integer page, Integer size, String userId, LocalDate startDate, LocalDate endDate,
                           String transactionId, List<Currency> currencies, List<TransferType> types, List<Status> statuses) {
        PageRequestDto pageRequestDto = new PageRequestDto(page, size);
        FilterDto filterDto = FilterDto.builder()
                .userId(userId)
                .startDate(startDate)
                .endDate(endDate)
                .transactionId(transactionId)
                .currencies(currencies)
                .types(types)
                .statuses(statuses)
                .build();

        var requestDto = RequestDto.<FilterDto>builder()
                .pageRequestDto(pageRequestDto)
                .data(filterDto)
                .build();
        var request = Objects.requireNonNull(paymentHistoryClient.getAllWithPageByFilter(requestDto).getBody());
        List<PaymentHistoryDto> response = new ArrayList<>();
        for (PaymentHistory ph : request.getContent()) {
            var dto = PaymentHistoryDto.builder()
                    .id(ph.getId())
                    .amount(ph.getAmount())
                    .userId(ph.getUserId())
                    .amount(ph.getAmount())
                    .toUser(ph.getToUser())
                    .requestField(ph.getRequestField())
                    .senderRequestId(ph.getSenderRequestId())
                    .vendorName(serviceClient.getVendorNameById(ph.getVendorId()).getData().getVendorName())
                    .serviceName(serviceClient.getNameById(ph.getServiceId()).getData().getName())
                    .transferType(ph.getTransferType())
                    .paymentDate(ph.getPaymentDate())
                    .status(ph.getStatus())
                    .build();
            response.add(dto);
        }

        return PageResponse.<List<PaymentHistoryDto>>builder()
                .totalPages(request.getTotalPages())
                .totalElements(request.getTotalElements())
                .content(response)
                .build();
    }

    public Map<String, BigDecimal> getCategoryStatistics(String userId, LocalDate startDate, LocalDate endDate) {
        var statisticCriteria = StatisticCriteria.builder()
                .userId(userId)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return paymentHistoryClient.getCategoryStatistics(statisticCriteria).getBody();
    }

    public PageResponse<Map<String, BigDecimal>> getServiceStatics(Integer page, Integer size, Integer serviceId,
                                                                   LocalDate startDate, LocalDate endDate) {
        var statisticCriteria = StatisticCriteria.builder()
                .serviceId(serviceId)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        var pageRequestDto = new PageRequestDto(page, size);
        var dto = RequestDto.<StatisticCriteria>builder()
                .pageRequestDto(pageRequestDto)
                .data(statisticCriteria)
                .build();
        var request = paymentHistoryClient.getServiceStatics(dto).getBody();
        Map<String, BigDecimal> response = new HashMap<>();
        assert request != null;
        for (Integer id : request.getContent().keySet()) {
            var serviceName = serviceClient.getNameById(id).getData().getName();
            response.put(serviceName, request.getContent().get(id));
        }
        return PageResponse.<Map<String, BigDecimal>>builder()
                .totalPages(request.getTotalPages())
                .totalElements(request.getTotalElements())
                .content(response)
                .build();
    }
}
