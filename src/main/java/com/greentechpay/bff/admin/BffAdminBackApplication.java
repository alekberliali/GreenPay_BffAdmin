package com.greentechpay.bff.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.greentechpay.bff.admin.client")
public class BffAdminBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(BffAdminBackApplication.class, args);
    }
}
