package com.msafiri.product.exception;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomExceptionHandlerTest {

    @Test
    void handleInvalidArgument() {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();

        BindingResult bindingResult = new BeanPropertyBindingResult(null, "objectName");

        FieldError fieldError = new FieldError("productRequest", "name", "Name is required");
        FieldError fieldError1 = new FieldError("productRequest", "description", "Description is required");
        FieldError fieldError2 = new FieldError("productRequest", "price", "Price must be greater than 0");

        bindingResult.addError(fieldError);
        bindingResult.addError(fieldError1);
        bindingResult.addError(fieldError2);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        Optional<Map<String, String>> resultOptional = customExceptionHandler.handleInvalidArgument(ex);

        assertTrue(resultOptional.isPresent());

        Map<String, String> errors = resultOptional.get();

        assertEquals(3, errors.size());
        assertEquals("Name is required", errors.get("name"));
        assertEquals("Description is required", errors.get("description"));
        assertEquals("Price must be greater than 0", errors.get("price"));

    }
}