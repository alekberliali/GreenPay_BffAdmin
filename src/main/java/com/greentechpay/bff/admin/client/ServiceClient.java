package com.greentechpay.bff.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//TODO url config
@FeignClient(value = "service", url = "${app.feign.service-provider.config.url}")

public interface ServiceClient {

    @GetMapping("/service-name/{id}")
    String getNameById(@PathVariable Integer id);

    @GetMapping("/vendor-name/{id}")
    String getVendorNameById(@PathVariable Integer id);
}
