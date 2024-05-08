package com.grenntechpay.bffadmin.controller;

import com.grenntechpay.bffadmin.dto.request.WalletLimitDto;
import com.grenntechpay.bffadmin.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/wallet")
public class WalletController {

    private final WalletService walletService;

    @PutMapping("/wallet-status")
    ResponseEntity<Boolean> updateWalletStatus(@RequestBody String userId) {
        return ResponseEntity.ok(walletService.updateWalletStatus(userId));
    }

    @PutMapping("/limit-balance")
    HttpStatus updateLimitByUserId(@RequestBody WalletLimitDto walletLimitDto) {
        return walletService.updateLimitBalance(walletLimitDto);
    }

}
