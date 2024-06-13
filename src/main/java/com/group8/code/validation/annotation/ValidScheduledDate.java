package com.group8.code.validation.annotation;

import com.group8.code.validation.validator.ScheduledDateValidator;
import com.group8.code.validation.validator.YearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ScheduledDateValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidScheduledDate {
    String message() default "Invalid scheduled date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // New attribute for max advance time in minutes
    int maxAdvanceTime() default 30;
}
