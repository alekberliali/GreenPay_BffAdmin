package com.greentechpay.bff.admin.client;

import com.greentechpay.bff.admin.client.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//TODO url config
@FeignClient(value = "service", url = "${app.feign.service-provider.config.url}")

public interface ServiceClient {

    @GetMapping("/Service/GetServiceNameById/{id}")
    BaseResponse<String> getNameById(@PathVariable Integer id);

    @GetMapping("/Vendors/GetVendorNameByIde/{id}")
    BaseResponse<String> getVendorNameById(@PathVariable Integer id);
}
