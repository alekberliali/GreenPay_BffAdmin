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
    private final WalletService walletService;

    private Map<String, String> getPhoneNumbersByIbanList(String agentName, String agentPassword, String agentId,
                                                          String accessToken, String authorization,
                                                          List<PaymentHistory> paymentHistoryList) {
        Set<String> ibanSet = new HashSet<>();
        for (var ph : paymentHistoryList) {
            ibanSet.add(ph.getSenderIban());
            ibanSet.add(ph.getReceiverIban());
        }
        var request = Wallet.builder()
                .iban(ibanSet.stream().toList())
                .build();
        return walletService.getPhoneNumberByIban(agentName, agentPassword, agentId, accessToken, authorization, request);
    }

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
        if (!requestIdSet.isEmpty()) {
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
        if (!requestIdSet.isEmpty()) {
            var idList = RequestIdList.builder()
                    .vendorIds(requestIdSet.stream().toList())
                    .build();
            return serviceClient.getVendorNamesById(agentName, agentPassword, agentId, accessToken, idList)
                    .getData().getVendorsName();
        } else return new HashMap<>();
    }

    public PageResponse<List<PaymentHistoryDto>>
    getAllWithPageByFilter(String agentName, String agentPassword, String agentId, String accessToken, String authorization,
                           Integer page, Integer size, String userId, Integer vendorId, Long merchantId, String categoryName,
                           List<Integer> serviceIdList, LocalDate startDate, LocalDate endDate, String receiptId,
                           String transactionId, List<Currency> currencies, List<TransferType> types, List<Status> statuses) {

        PageRequestDto pageRequestDto = new PageRequestDto(page, size);
        FilterDto filterDto = FilterDto.builder()
                .userId(userId)
                .receiptId(receiptId)
                .vendorId(vendorId)
                .merchantId(merchantId)
                .startDate(startDate)
                .endDate(endDate)
                .transactionId(transactionId)
                .serviceIdList(serviceIdList)
                .categoryName(categoryName)
                .currencies(currencies)
                .transferTypes(types)
                .statuses(statuses)
                .build();

        var requestDto = RequestDto.<FilterDto>builder()
                .pageRequestDto(pageRequestDto)
                .data(filterDto)
                .build();
        var request = Objects.requireNonNull(paymentHistoryClient.getAllWithPageByFilter(requestDto));
        Map<Integer, String> serviceMap = getServiceNames(agentName, agentPassword, agentId, accessToken, request.getContent());
        serviceMap.put(null, "");
        Map<Integer, String> vendorMap = getVendorNames(agentName, agentPassword, agentId, accessToken, request.getContent());
        vendorMap.put(null, "");
        Map<Long, String> merchantMap = getMerchantNames(agentName, agentPassword, agentId, accessToken, request.getContent());
        Map<String, String> ibanMap = getPhoneNumbersByIbanList(agentName, agentPassword, agentId, accessToken,
                authorization, request.getContent());
        List<PaymentHistoryDto> response = new ArrayList<>();
        for (PaymentHistory ph : request.getContent()) {
            String phoneNumber;
            if (ph.getSenderIban() != null) {
                phoneNumber = ibanMap.get(ph.getSenderIban());
            } else phoneNumber = null;

            var dto = PaymentHistoryDto.builder()
                    .id(ph.getId())
                    .amount(ph.getAmount())
                    .vendorName(vendorMap.get(ph.getVendorId()))
                    .serviceName(serviceMap.get(ph.getServiceId()))
                    .merchantName(merchantMap.get(ph.getMerchantId()))
                    .senderPhoneNumber(phoneNumber)
                    .requestField(ph.getRequestField())
                    .transferType(ph.getTransferType())
                    .updateDate(ph.getUpdateDate())
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

    public PaymentHistoryDto getPaymentHistoryById(String agentName, String agentPassword, String agentId,
                                                   String accessToken, String authorization, Long id, Long merchantId) {
        var request = paymentHistoryClient.getById(id, merchantId);
        Map<Integer, String> serviceMap = getServiceNames(agentName, agentPassword, agentId, accessToken, List.of(request));
        serviceMap.put(null, "");
        Map<Integer, String> vendorMap = getVendorNames(agentName, agentPassword, agentId, accessToken, List.of(request));
        vendorMap.put(null, "");
        Map<Long, String> merchantMap = getMerchantNames(agentName, agentPassword, agentId, accessToken, List.of(request));
        Map<String, String> ibanMap = getPhoneNumbersByIbanList(agentName, agentPassword, agentId, accessToken,
                authorization, List.of(request));
        String phoneNumber;
        if (request.getSenderIban() != null) {
            phoneNumber = ibanMap.get(request.getSenderIban());
        } else phoneNumber = null;
        return PaymentHistoryDto.builder()
                .receiptId(request.getTransactionId().substring(0, 8))
                .amount(request.getAmount())
                .paymentDate(request.getPaymentDate())
                .updateDate(request.getUpdateDate())
                .senderRequestId(request.getSenderRequestId())
                .transactionId(request.getTransactionId())
                .externalPaymentId(request.getExternalPaymentId())
                .serviceName(serviceMap.get(request.getServiceId()))
                .categoryName(request.getCategoryName())
                .senderPhoneNumber(phoneNumber)
                .requestField(request.getRequestField())
                .senderIban(request.getSenderIban())
                .receiverIban(request.getReceiverIban())
                .merchantName(merchantMap.get(request.getMerchantId()))
                .vendorName(vendorMap.get(request.getVendorId()))
                .currency(request.getCurrency())
                .transferType(request.getTransferType())
                .status(request.getStatus())
                .build();
    }

    public Map<String, BigDecimal> getCategoryStatistics(String userId, LocalDate startDate, LocalDate endDate,
                                                         Currency currency) {
        var statisticCriteria = StatisticCriteria.builder()
                .userId(userId)
                .startDate(startDate)
                .endDate(endDate)
                .currency(currency)
                .build();
        return paymentHistoryClient.getCategoryStatistics(statisticCriteria).getBody();
    }

    public Map<String, BigDecimal> getMerchantStatistics(String agentName, String agentPassword, String agentId,
                                                         String accessToken, LocalDate startDate, LocalDate endDate,
                                                         Currency currency) {
        var statisticsCriteria = StatisticCriteria.builder()
                .startDate(startDate)
                .endDate(endDate)
                .currency(currency)
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
                                                     LocalDate startDate, LocalDate endDate, Currency currency) {
        var statisticCriteria = StatisticCriteria.builder()
                .serviceIdList(serviceIdList)
                .vendorId(vendorId)
                .startDate(startDate)
                .endDate(endDate)
                .currency(currency)
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
                                                                          LocalDate startDate, LocalDate endDate,
                                                                          Currency currency) {
        var statisticsCriteria = StatisticCriteria.builder()
                .categoryName(categoryName)
                .vendorId(vendorId)
                .startDate(startDate)
                .endDate(endDate)
                .currency(currency)
                .build();
        return paymentHistoryClient.getCategoryStatisticsByName(statisticsCriteria).getBody();
    }
}
