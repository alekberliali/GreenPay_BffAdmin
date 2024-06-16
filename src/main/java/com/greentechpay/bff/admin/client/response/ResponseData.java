package com.greentechpay.bff.admin.client.response;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseData {
    private Map<Integer, String> names;
}
