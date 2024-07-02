package com.greentechpay.bff.admin.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageResponse<T> {
    private Integer totalPages;
    private Long totalElements;
    private T content;
}
