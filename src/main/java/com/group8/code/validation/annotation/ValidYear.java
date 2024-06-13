package com.group8.code.validation.annotation;

import com.group8.code.validation.validator.YearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = YearValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidYear {
    String message() default "El año debe estar entre {minYear} y {maxYear}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int minYear() default 1886;
    int maxYear() default Integer.MAX_VALUE;
}