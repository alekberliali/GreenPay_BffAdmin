package com.grenntechpay.bffadmin.client;

import com.grenntechpay.bffadmin.dto.request.RequestResponse;
import com.grenntechpay.bffadmin.dto.request.RequestResponseWalletLimit;
import com.grenntechpay.bffadmin.dto.request.RequestResponseWalletStatus;
import com.grenntechpay.bffadmin.dto.request.WalletLimitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "wallet", url = "${app.feign.wallet.config.url}")
public interface WalletClient {
    @PutMapping(value = "/WalletStatus")
    RequestResponse<RequestResponseWalletStatus> updateWalletStatus(@RequestBody String userId);

    @PutMapping(value = "/limitBalance")
    RequestResponse<RequestResponseWalletLimit> updateWalletBalance(WalletLimitDto walletLimitDto);
}
