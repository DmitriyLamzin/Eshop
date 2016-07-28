package org.lamzin.eshop.validation;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Dmitriy on 30.03.2016.
 */
@Transactional (propagation = Propagation.NOT_SUPPORTED)
public class ExistenceValidation implements ConstraintValidator<IsNotPersist, Object> {
    private final Logger log = Logger.getLogger(this.getClass());


    @PersistenceContext
    EntityManager em;


    Class type;
    String fieldName;
    String serviceQualifier;
    String serviceName;


    public void initialize(IsNotPersist parameters){
        this.fieldName = parameters.fieldName();
        this.type = parameters.typeName();
    }

    public boolean isValid(Object string, ConstraintValidatorContext context){
        log.debug(em.find(type, string) == null);
        return em.find(type, string) == null;
    }
}
