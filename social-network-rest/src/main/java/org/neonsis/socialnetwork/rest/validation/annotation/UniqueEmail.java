package org.neonsis.socialnetwork.rest.validation.annotation;

import org.neonsis.socialnetwork.rest.validation.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({FIELD, METHOD})
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {

    String message() default "{user.email.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
