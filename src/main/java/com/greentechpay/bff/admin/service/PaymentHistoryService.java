package com.greentechpay.bff.admin.service;

import com.greentechpay.bff.admin.client.ServiceClient;
import com.greentechpay.bff.admin.client.request.*;
import com.greentechpay.bff.admin.client.PaymentHistoryClient;
import com.greentechpay.bff.admin.dto.Currency;
import com.greentechpay.bff.admin.dto.Status;
import com.greentechpay.bff.admin.dto.TransferType;
import com.greentechpay.bff.admin.client.response.PaymentHistory;
import com.greentechpay.bff.admin.dto.response.PageResponse;
import com.greentechpay.bff.admin.dto.response.PaymentHistoryDto;
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

    private Map<Integer, String> getNamesFromServiceProvider(List<PaymentHistory> paymentHistoryList, RequestType type) {
        List<Integer> requestIdList = requestIdList(paymentHistoryList, type).stream().toList();
        return serviceClient.getNameById(new NameIdListDto(requestIdList, type)).getData().getNames();
    }

    private Set<Integer> requestIdList(List<PaymentHistory> paymentHistoryList, RequestType type) {
        Set<Integer> set = new HashSet<>();
        if (type.equals(RequestType.Service)) {
            for (var ph : paymentHistoryList) {
                set.add(ph.getServiceId());
            }
            return set;
        } else if (type.equals(RequestType.Vendor)) {
            for (var ph : paymentHistoryList) {
                set.add(ph.getVendorId());
            }
            return set;
        } else {
            for (var ph : paymentHistoryList) {
                set.add(ph.getMerchantId());
            }
            return set;
        }
    }

    public PageResponse<List<PaymentHistoryDto>>
    getAllWithPageByFilter(Integer page, Integer size, String userId, Integer vendorId, Integer merchantId, LocalDate startDate, LocalDate endDate,
                           String transactionId, List<Currency> currencies, List<TransferType> types, List<Status> statuses) {
        PageRequestDto pageRequestDto = new PageRequestDto(page, size);
        FilterDto filterDto = FilterDto.builder()
                .userId(userId)
                .vendorId(vendorId)
                .merchantId(merchantId)
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
        Map<Integer, String> serviceMap = getNamesFromServiceProvider(request.getContent(), RequestType.Service);
        Map<Integer, String> vendorMap = getNamesFromServiceProvider(request.getContent(), RequestType.Vendor);
        Map<Integer, String> merchantMap = getNamesFromServiceProvider(request.getContent(), RequestType.Merchant);
        List<PaymentHistoryDto> response = new ArrayList<>();
        for (PaymentHistory ph : request.getContent()) {
            var dto = PaymentHistoryDto.builder()
                    .amount(ph.getAmount())
                    .userId(ph.getUserId())
                    .amount(ph.getAmount())
                    .toUser(ph.getToUser())
                    .senderIban(ph.getSenderIban())
                    .receiverIban(ph.getReceiverIban())
                    .requestField(ph.getRequestField())
                    .senderRequestId(ph.getSenderRequestId())
                    .vendorName(vendorMap.get(ph.getVendorId()))
                    .serviceName(serviceMap.get(ph.getServiceId()))
                    .merchantName(merchantMap.get(ph.getMerchantId()))
                    .transactionId(ph.getTransactionId())
                    .transferType(ph.getTransferType())
                    .paymentDate(ph.getPaymentDate())
                    .currency(ph.getCurrency())
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

    public Map<String, BigDecimal> getMerchantStatistics(LocalDate startDate, LocalDate endDate) {
        var statisticsCriteria = StatisticCriteria.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();
        var request = paymentHistoryClient.getMerchantStatistics(statisticsCriteria).getBody();
        assert request != null;
        Set<Integer> set = request.keySet();
        Map<Integer, String> merchantMap = serviceClient.getNameById(new NameIdListDto(set.stream().toList(),
                RequestType.Merchant)).getData().getNames();
        Map<String, BigDecimal> response = new HashMap<>();
        for (Integer id : set) {
            var merchantName = merchantMap.get(id);
            response.put(merchantName, request.get(id));
        }
        return response;
    }

    public Map<String, BigDecimal> getServiceStatics(List<Integer> serviceIdList, Integer vendorId, LocalDate startDate, LocalDate endDate) {
        var statisticCriteria = StatisticCriteria.builder()
                .serviceIdList(serviceIdList)
                .vendorId(vendorId)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        var request = paymentHistoryClient.getServiceStatics(statisticCriteria).getBody();
        Set<Integer> set = Objects.requireNonNull(request).keySet();
        Map<Integer, String> serviceMap = serviceClient.getNameById(new NameIdListDto(set.stream().toList(),
                RequestType.Service)).getData().getNames();
        Map<String, BigDecimal> response = new HashMap<>();
        for (Integer id : set) {
            var serviceName = serviceMap.get(id);
            response.put(serviceName, request.get(id));
        }
        return response;
    }

    public Map<LocalDate, BigDecimal> getCategoryStatisticsByCategoryName(String categoryName, Integer vendorId,
                                                                          LocalDate startDate, LocalDate endDate) {
        var statisticsCriteria = StatisticCriteria.builder()
                .categoryName(categoryName)
                .vendorId(vendorId)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return paymentHistoryClient.getCategoryStatisticsByName(statisticsCriteria).getBody();
    }
}
