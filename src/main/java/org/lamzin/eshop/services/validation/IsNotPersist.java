package org.lamzin.eshop.services.validation;

import org.lamzin.eshop.services.ExistenceCheckable;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * Created by Dmitriy on 30.03.2016.
 */
@Constraint(validatedBy = ExistenceValidation.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface IsNotPersist {
    String message() default "The entity with this data is already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
//    Class<? extends ExistenceCheckable> service();
    String fieldName() default "id";
    String serviceName() default "";
}
