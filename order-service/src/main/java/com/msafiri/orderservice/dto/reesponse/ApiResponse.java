package com.msafiri.orderservice.dto.reesponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private Object data;

    public ApiResponse(String msg) {
        this.message = msg;
    }

    public static ApiResponse successResponse(String msg, Object data) {
        return new ApiResponse(msg, data);
    }

    public static ApiResponse successResponse(String msg) {
        return new ApiResponse(msg);
    }

    public static ApiResponse errorResponse(String message) {
        return new ApiResponse(message, null);
    }
}
