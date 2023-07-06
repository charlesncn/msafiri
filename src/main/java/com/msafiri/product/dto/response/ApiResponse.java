package com.msafiri.product.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private HttpStatus status;
    private String message;
    private Object data;

    public static ApiResponse successResponse(HttpStatus status, String message, Object data) {
        return new ApiResponse(status, message, data);
    }

    public static ApiResponse errorResponse(HttpStatus status, String message) {
        return new ApiResponse(status, message, null);
    }
}
