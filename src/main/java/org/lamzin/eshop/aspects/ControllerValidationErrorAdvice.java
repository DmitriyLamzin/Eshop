package org.lamzin.eshop.aspects;

import org.lamzin.eshop.entites.dto.ValidationErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Created by Dmitriy on 31.03.2016.
 */
@ControllerAdvice
public class ControllerValidationErrorAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO();

        for (FieldError fieldError: fieldErrors) {
            dto.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return dto;
    }

}
