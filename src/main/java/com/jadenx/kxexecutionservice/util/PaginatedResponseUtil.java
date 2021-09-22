package com.jadenx.kxexecutionservice.util;

import com.jadenx.kxexecutionservice.model.PaginatedResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.util.List;

@UtilityClass
public class PaginatedResponseUtil {
    public static PaginatedResponse<?> paginatedResponse(final List<?> dtoList, final Page<?> page) {
        return  PaginatedResponse.builder()
            .data(dtoList)
            .totalPages(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .page(page.getNumber())
            .size(page.getSize())
            .build();
    }
}
