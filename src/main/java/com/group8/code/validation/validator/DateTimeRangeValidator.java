package com.group8.code.validation.validator;

import com.group8.code.validation.annotation.DateTimeRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeRangeValidator implements ConstraintValidator<DateTimeRange, String> {
    private int minMinutes;
    private int maxMinutes;

    @Override
    public void initialize(DateTimeRange constraintAnnotation) {
        this.minMinutes = constraintAnnotation.minMinutes();
        this.maxMinutes = constraintAnnotation.maxMinutes();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // valores nulos o vacíos son válidos, use @NotNull para verificaciones nulas
        }

        ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(value, formatter);
            LocalDateTime now = LocalDateTime.now();

            LocalDateTime minDateTime = now.minus(minMinutes, ChronoUnit.MINUTES);
            LocalDateTime maxDateTime = now.plus(maxMinutes, ChronoUnit.MINUTES);

            return (dateTime.isEqual(minDateTime) || dateTime.isAfter(minDateTime)) &&
                    (dateTime.isEqual(maxDateTime) || dateTime.isBefore(maxDateTime));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}