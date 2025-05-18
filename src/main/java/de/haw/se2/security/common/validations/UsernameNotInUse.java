package de.haw.se2.security.common.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameNotInUseValidator.class)
public @interface UsernameNotInUse {
    String message() default "Username is already in use";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
