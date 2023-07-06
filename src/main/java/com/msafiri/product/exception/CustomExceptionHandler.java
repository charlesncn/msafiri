package com.msafiri.product.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Optional<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Optional<MethodArgumentNotValidException> optional = Optional.ofNullable(ex);
        Map<String, String> errors = new HashMap<>();
        if (optional.isPresent())
            errors = optional.get().getBindingResult().getFieldErrors()
                    .stream().collect(HashMap::new, (m, v) -> m.put(v.getField(), v.getDefaultMessage()), HashMap::putAll);
        return Optional.of(errors);
    }
}
