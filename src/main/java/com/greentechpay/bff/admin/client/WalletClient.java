package com.greentechpay.bff.admin.client;

import com.greentechpay.bff.admin.client.response.BaseResponse;
import com.greentechpay.bff.admin.client.response.ResponseData;
import com.greentechpay.bff.admin.dto.request.WalletLimitDto;
import com.greentechpay.bff.admin.client.response.ResponseWalletLimit;
import com.greentechpay.bff.admin.client.response.ResponseWalletStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "wallet", url = "${app.feign.wallet.config.url}")
public interface WalletClient {

    @GetMapping("/IbanAccount/GetPhoneNumberByIban")
    BaseResponse<ResponseData> getPhoneNumberByIban(@RequestHeader(value = "agent-name") String agentName,
                                                    @RequestHeader(value = "agent-password") String agentPassword,
                                                    @RequestHeader(value = "agent-id") String agentId,
                                                    @RequestHeader(value = "access-token") String accessToken,
                                                    @RequestHeader(value = "Authorization") String authorization,
                                                    @RequestParam String iban);
}
