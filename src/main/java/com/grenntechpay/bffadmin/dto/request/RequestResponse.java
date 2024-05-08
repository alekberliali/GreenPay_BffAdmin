package com.grenntechpay.bffadmin.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RequestResponse<T> {
    private T data;
    private List<String> errors;
}
