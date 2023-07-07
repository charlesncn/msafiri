package com.msafiri.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Optional<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Optional<MethodArgumentNotValidException> optional = Optional.ofNullable(ex);
        Map<String, String> errors = new HashMap<>();
        if (optional.isPresent())
            errors = optional.get().getBindingResult().getFieldErrors()
                    .stream().collect(HashMap::new, (m, v) -> m.put(v.getField(), v.getDefaultMessage()), HashMap::putAll);
        return Optional.of(errors);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public Optional<Map<String, String>> handleProductNotFound(ProductNotFoundException ex) {
        Optional<ProductNotFoundException> optional = Optional.ofNullable(ex);
        Map<String, String> errors = new HashMap<>();
        optional.ifPresent(e -> errors.put("message", e.getMessage()));
        return Optional.of(errors);
    }
}
