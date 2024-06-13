package com.group8.code.validation.validator;

import com.group8.code.validation.annotation.ValidScheduledDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduledDateValidator implements ConstraintValidator<ValidScheduledDate, String> {

    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    private long maxAdvanceTimeMillis;

    @Override
    public void initialize(ValidScheduledDate constraintAnnotation) {
        this.maxAdvanceTimeMillis = constraintAnnotation.maxAdvanceTime() * 60 * 1000; // Convert minutes to milliseconds
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Allow null and empty values, use @NotEmpty or @NotNull for that
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);

        try {
            Date scheduledDate = sdf.parse(value);
            Date now = new Date();
            long timeDifference = scheduledDate.getTime() - now.getTime();

            // Check if the scheduled date is within the max advance time in the past or anytime in the future
            return timeDifference >= -maxAdvanceTimeMillis;
        } catch (ParseException e) {
            return false; // Invalid date format
        }
    }
}
