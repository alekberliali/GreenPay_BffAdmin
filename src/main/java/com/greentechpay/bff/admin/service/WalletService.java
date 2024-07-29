package com.greentechpay.bff.admin.service;

import com.greentechpay.bff.admin.client.WalletClient;
import com.greentechpay.bff.admin.client.request.Wallet;
import com.greentechpay.bff.admin.dto.request.WalletLimitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletClient walletClient;

    protected Map<String, String> getPhoneNumberByIban(String agentName, String agentPassword, String agentId,
                                                       String accessToken, String authorizationToken, Wallet wallet) {
        return walletClient.getPhoneNumberByIban(agentName, agentPassword, agentId, accessToken,
                authorizationToken, wallet).getData().getIbanPhonePairs();
    }
}
