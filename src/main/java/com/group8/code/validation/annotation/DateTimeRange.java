package com.group8.code.validation.annotation;


import com.group8.code.validation.validator.DateTimeRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateTimeRangeValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateTimeRange {
    String message() default "La fecha y hora deben estar dentro del rango permitido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int minMinutes() default 0;
    int maxMinutes() default Integer.MAX_VALUE;
}
