package com.greentechpay.bff.admin.client.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestDto<T> {
    private PageRequestDto pageRequestDto;
    private T data;
}
