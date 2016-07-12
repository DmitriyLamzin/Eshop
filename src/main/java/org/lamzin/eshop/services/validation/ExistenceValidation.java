package org.lamzin.eshop.services.validation;

import org.lamzin.eshop.services.ExistenceCheckable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Dmitriy on 30.03.2016.
 */
@Transactional (propagation = Propagation.NOT_SUPPORTED)
public class ExistenceValidation implements ConstraintValidator<IsNotPersist, Object> {

    @Autowired
    ApplicationContext applicationContext;

    ExistenceCheckable service;
    String fieldName;
    String serviceQualifier;
    String serviceName;


    public void initialize(IsNotPersist parameters){
        this.fieldName = parameters.fieldName();
        this.serviceQualifier = parameters.serviceName();
        this.serviceName = parameters.serviceName();

        Object service = applicationContext.getBean(serviceName);
        if (service instanceof ExistenceCheckable){
            this.service = (ExistenceCheckable) service;
        }
        else throw new IllegalArgumentException(serviceName);
    }

    public boolean isValid(Object string, ConstraintValidatorContext context){
        return !service.exists(string);
    }
}
