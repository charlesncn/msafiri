package com.msafiri.product.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String message;
    private Object data;

    public static ApiResponse successResponse(Object data) {
        return new ApiResponse(null, data);
    }

    public static ApiResponse errorResponse(String message) {
        return new ApiResponse(message, null);
    }
}
