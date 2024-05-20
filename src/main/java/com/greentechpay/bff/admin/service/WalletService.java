package com.greentechpay.bff.admin.service;

import com.greentechpay.bff.admin.client.WalletClient;
import com.greentechpay.bff.admin.dto.request.WalletLimitDto;
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

    //TODO write circuit breaker
    public HttpStatus updateLimitBalance(WalletLimitDto walletLimitDto) {
        var dto = walletClient.updateWalletBalance(walletLimitDto);
        if (dto.getData() != null) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
