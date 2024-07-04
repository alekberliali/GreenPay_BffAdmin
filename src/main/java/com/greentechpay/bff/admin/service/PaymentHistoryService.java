package com.greentechpay.bff.admin.service;

import com.greentechpay.bff.admin.client.ServiceClient;
import com.greentechpay.bff.admin.client.request.*;
import com.greentechpay.bff.admin.client.PaymentHistoryClient;
import com.greentechpay.bff.admin.dto.Currency;
import com.greentechpay.bff.admin.client.request.RequestIdList;
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

    private Map<Long, String> getMerchantNames(String agentName, String agentPassword, String agentId,
                                               String accessToken, List<PaymentHistory> paymentHistoryList) {

        List<Long> requestIdList = new ArrayList<>();
        for (var ph : paymentHistoryList) {
            requestIdList.add(ph.getMerchantId());
        }
        var idList = RequestIdList.builder()
                .merchantIds(requestIdList)
                .build();
        return serviceClient.getMerchantNamesById(agentName, agentPassword, agentId, accessToken, idList)
                .getData().getMerchantsName();
    }

    private Map<Integer, String> getServiceNames(String agentName, String agentPassword, String agentId,
                                                 String accessToken, List<PaymentHistory> paymentHistoryList) {
        Set<Integer> requestIdSet = new HashSet<>();
        for (var ph : paymentHistoryList) {
            requestIdSet.add(ph.getServiceId());
        }
        requestIdSet.remove(null);
        if (requestIdSet.size() > 0) {
            var idList = RequestIdList.builder()
                    .serviceIds(requestIdSet.stream().toList())
                    .build();
            return serviceClient.getServiceNamesById(agentName, agentPassword, agentId, accessToken, idList)
                    .getData().getServicesName();
        } else return new HashMap<>();
    }

    private Map<Integer, String> getVendorNames(String agentName, String agentPassword, String agentId,
                                                String accessToken, List<PaymentHistory> paymentHistoryList) {
        Set<Integer> requestIdSet = new HashSet<>();

        for (var ph : paymentHistoryList) {
            requestIdSet.add(ph.getVendorId());
        }
        requestIdSet.remove(null);
        if (requestIdSet.size() > 0) {
            var idList = RequestIdList.builder()
                    .vendorIds(requestIdSet.stream().toList())
                    .build();
            return serviceClient.getVendorNamesById(agentName, agentPassword, agentId, accessToken, idList)
                    .getData().getVendorsName();
        } else return new HashMap<>();
    }

    public PageResponse<List<PaymentHistoryDto>>
    getAllWithPageByFilter(String agentName, String agentPassword, String agentId, String accessToken, Integer page,
                           Integer size, String userId, Integer vendorId, Long merchantId, LocalDate startDate, LocalDate endDate,
                           String transactionId, List<Currency> currencies, List<TransferType> types, List<Status> statuses) {
        if (merchantId != null && !Objects.equals(merchantId, Long.valueOf(agentId))) {
            throw new RuntimeException("merchant id is not acceptable for this user");
        }
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
        Map<Integer, String> serviceMap = getServiceNames(agentName, agentPassword, agentId, accessToken, request.getContent());
        serviceMap.put(null, "");
        Map<Integer, String> vendorMap = getVendorNames(agentName, agentPassword, agentId, accessToken, request.getContent());
        vendorMap.put(null, "");
        Map<Long, String> merchantMap = getMerchantNames(agentName, agentPassword, agentId, accessToken, request.getContent());
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
                    .externalPaymentId(ph.getExternalPaymentId())
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

    public Map<String, BigDecimal> getMerchantStatistics(String agentName, String agentPassword, String agentId,
                                                         String accessToken, LocalDate startDate, LocalDate endDate) {
        var statisticsCriteria = StatisticCriteria.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();
        var request = paymentHistoryClient.getMerchantStatistics(statisticsCriteria).getBody();
        assert request != null;
        Set<Long> set = request.keySet();
        var idList = RequestIdList.builder()
                .merchantIds(set.stream().toList())
                .build();
        Map<Long, String> merchantMap = serviceClient.getMerchantNamesById(agentName, agentPassword, agentId,
                accessToken, idList).getData().getMerchantsName();
        Map<String, BigDecimal> response = new HashMap<>();
        for (Long id : set) {
            var merchantName = merchantMap.get(id);
            response.put(merchantName, request.get(id));
        }
        return response;
    }

    public Map<String, BigDecimal> getServiceStatics(String agentName, String agentPassword, String agentId,
                                                     String accessToken, List<Integer> serviceIdList, Integer vendorId,
                                                     LocalDate startDate, LocalDate endDate) {
        var statisticCriteria = StatisticCriteria.builder()
                .serviceIdList(serviceIdList)
                .vendorId(vendorId)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        var request = paymentHistoryClient.getServiceStatics(statisticCriteria).getBody();
        Set<Integer> set = Objects.requireNonNull(request).keySet();
        var idList = RequestIdList.builder()
                .serviceIds(set.stream().toList())
                .build();
        Map<Integer, String> serviceMap = serviceClient.getServiceNamesById(agentName, agentPassword, agentId,
                accessToken, idList).getData().getServicesName();
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
