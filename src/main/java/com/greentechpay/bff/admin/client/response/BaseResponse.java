package com.greentechpay.bff.admin.client.response;

import lombok.Data;

import java.util.List;

@Data
public class BaseResponse<T> {
    private T data;
    private List<String> errors;
}
