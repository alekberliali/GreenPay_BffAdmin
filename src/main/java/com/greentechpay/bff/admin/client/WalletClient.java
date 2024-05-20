package com.greentechpay.bff.admin.client;

import com.greentechpay.bff.admin.client.response.BaseResponse;
import com.greentechpay.bff.admin.dto.request.WalletLimitDto;
import com.greentechpay.bff.admin.client.response.ResponseWalletLimit;
import com.greentechpay.bff.admin.client.response.ResponseWalletStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "wallet", url = "${app.feign.wallet.config.url}")
public interface WalletClient {
    @PutMapping(value = "/wallet/walletStatus")
    BaseResponse<ResponseWalletStatus> updateWalletStatus(@RequestBody String userId);

    @PutMapping(value = "wallet/limitBalance")
    BaseResponse<ResponseWalletLimit> updateWalletBalance(WalletLimitDto walletLimitDto);
}
