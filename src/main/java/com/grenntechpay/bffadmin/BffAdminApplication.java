package com.grenntechpay.bffadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BffAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BffAdminApplication.class, args);
    }

}
