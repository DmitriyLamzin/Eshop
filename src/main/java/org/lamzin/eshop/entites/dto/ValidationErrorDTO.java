package org.lamzin.eshop.entites.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitriy on 31.03.2016.
 */
public class ValidationErrorDTO {
    private List<FieldErrorDTO> fieldErrorDTOs = new ArrayList<FieldErrorDTO>();

    public ValidationErrorDTO(){}

    public void addFieldError(String path, String message) {
        FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrorDTOs.add(error);
    }

    public List<FieldErrorDTO> getFieldErrorDTOs() {
        return fieldErrorDTOs;
    }
}
