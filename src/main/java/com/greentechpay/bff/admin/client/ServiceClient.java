package com.greentechpay.bff.admin.client;

import com.greentechpay.bff.admin.client.response.BaseResponse;
import com.greentechpay.bff.admin.client.response.ResponseData;
import com.greentechpay.bff.admin.client.request.RequestIdList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(value = "service", url = "${app.feign.service-provider.config.url}")
public interface ServiceClient {
    @PostMapping("/Vendors/GetVendorsNameById")
    BaseResponse<ResponseData> getVendorNamesById(@RequestHeader(value = "agent-name") String agentName,
                                                  @RequestHeader(value = "agent-password") String agentPassword,
                                                  @RequestHeader(value = "agent-id") String agentId,
                                                  @RequestHeader(value = "access-token") String accessToken,
                                                  @RequestBody RequestIdList requestIdList);

    @PostMapping("/Merchants/GetMerchantsNameById")
    BaseResponse<ResponseData> getMerchantNamesById(@RequestHeader(value = "agent-name") String agentName,
                                                    @RequestHeader(value = "agent-password") String agentPassword,
                                                    @RequestHeader(value = "agent-id") String agentId,
                                                    @RequestHeader(value = "access-token") String accessToken,
                                                    @RequestBody RequestIdList requestIdList);

    @PostMapping("/Service/GetServicesNameById")
    BaseResponse<ResponseData> getServiceNamesById(@RequestHeader(value = "agent-name") String agentName,
                                                   @RequestHeader(value = "agent-password") String agentPassword,
                                                   @RequestHeader(value = "agent-id") String agentId,
                                                   @RequestHeader(value = "access-token") String accessToken,
                                                   @RequestBody RequestIdList requestIdList);
}
