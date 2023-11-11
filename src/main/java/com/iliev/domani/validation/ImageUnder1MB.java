package com.iliev.domani.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ImageUnder1MbValidator.class)
public @interface ImageUnder1MB {

    String message() default "Image size must be under 1 MB.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
