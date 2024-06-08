package com.greentechpay.bff.admin.client;

import com.greentechpay.bff.admin.client.response.BaseResponse;
import com.greentechpay.bff.admin.client.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//TODO url config
@FeignClient(value = "service", url = "${app.feign.service-provider.config.url}")

public interface ServiceClient {

    @GetMapping("/Service/GetServiceNameById/{id}")
    BaseResponse<ResponseData> getNameById(@PathVariable Integer id);

    @GetMapping("/Vendors/GetVendorNameById/{id}")
    BaseResponse<ResponseData> getVendorNameById(@PathVariable Integer id);
}
