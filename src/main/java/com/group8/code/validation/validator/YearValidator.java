package com.group8.code.validation.validator;

import com.group8.code.validation.annotation.ValidYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class YearValidator implements ConstraintValidator<ValidYear, Integer> {

    private int minYear;
    private int maxYear;

    @Override
    public void initialize(ValidYear constraintAnnotation) {
        this.minYear = constraintAnnotation.minYear();
        this.maxYear = constraintAnnotation.maxYear() == Integer.MAX_VALUE ? Year.now().getValue() : constraintAnnotation.maxYear();
    }

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        if (year == null) {
            return false; // or true, depending on whether null is considered valid
        }
        return year >= minYear && year <= maxYear;
    }
}