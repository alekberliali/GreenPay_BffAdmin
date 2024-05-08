package com.grenntechpay.bffadmin.service;

import com.grenntechpay.bffadmin.client.WalletClient;
import com.grenntechpay.bffadmin.dto.request.WalletLimitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletClient walletClient;

    public Boolean updateWalletStatus(String id) {
        var dto = walletClient.updateWalletStatus(id);
        return dto.getData().getStatus();
    }

    //TODO write general exception handler.
    public HttpStatus updateLimitBalance(WalletLimitDto walletLimitDto) {
        var dto = walletClient.updateWalletBalance(walletLimitDto);
        if (dto.getData() != null) {
            return HttpStatus.OK;
        }else {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
