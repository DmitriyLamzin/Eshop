package org.lamzin.eshop.controller.rest;

import org.lamzin.eshop.dto.ValidationErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;

/**
 * Created by Dmitriy on 31.03.2016.
 */
@ControllerAdvice
public class ControllerValidationErrorAdvice {
    @Autowired
    private MessageSource messageSource;
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
            String localizedMessage = localizeMessage(fieldError.getCode());
            dto.addFieldError(fieldError.getField(), localizedMessage);
        }

        return dto;
    }

    private String localizeMessage(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage(code, null, locale);
        return errorMessage;
    }

}
