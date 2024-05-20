package com.greentechpay.bff.admin.controller;

import com.greentechpay.bff.admin.dto.request.WalletLimitDto;

import com.greentechpay.bff.admin.service.WalletService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final WalletService walletService;

    @PutMapping("/wallet-status")
    public ResponseEntity<Boolean>
    updateWalletStatus(@RequestBody @NotBlank(message = "user id can not be empty") String userId) {
        return ResponseEntity.ok(walletService.updateWalletStatus(userId));
    }

    @PutMapping("/limit-balance")
    HttpStatus updateLimitByUserId(@RequestBody WalletLimitDto walletLimitDto) {
        return walletService.updateLimitBalance(walletLimitDto);
    }

}
