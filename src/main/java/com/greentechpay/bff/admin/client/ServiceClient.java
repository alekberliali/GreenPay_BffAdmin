package com.greentechpay.bff.admin.client;

import com.greentechpay.bff.admin.client.request.NameIdListDto;
import com.greentechpay.bff.admin.client.request.RequestType;
import com.greentechpay.bff.admin.client.response.BaseResponse;
import com.greentechpay.bff.admin.client.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "service", url = "${app.feign.service-provider.config.url}")
public interface ServiceClient {
    @PostMapping("/Entities/GetEntityNames")
    BaseResponse<ResponseData> getNameById(NameIdListDto nameIdListDto);
}
