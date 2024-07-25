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

    protected String getPhoneNumberByIban(String agentName, String agentPassword, String agentId,
                                          String accessToken, String authorizationToken, String iban) {

        return walletClient.getPhoneNumberByIban(agentName, agentPassword, agentId, accessToken,
                authorizationToken, iban).getData().getPhoneNumber();
    }
}
