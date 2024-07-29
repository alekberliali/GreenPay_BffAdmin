package com.greentechpay.bff.admin.client.response;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseData {
    private Map<Integer, String> vendorsName;
    private Map<Integer, String> servicesName;
    private Map<Long, String> merchantsName;
    private Map<String,String> ibanPhonePairs;
}