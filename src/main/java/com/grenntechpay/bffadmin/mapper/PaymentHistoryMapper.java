package com.grenntechpay.bffadmin.mapper;

import com.grenntechpay.bffadmin.dto.request.RequestResponsePaymentHistoryDto;
import com.grenntechpay.bffadmin.dto.response.ResponsePaymentHistoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentHistoryMapper {
    ResponsePaymentHistoryDto requestToResponse(RequestResponsePaymentHistoryDto requestPaymentHistoryDto);
}
