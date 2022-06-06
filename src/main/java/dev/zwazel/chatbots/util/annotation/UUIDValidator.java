package dev.zwazel.chatbots.util.annotation;

import dev.zwazel.chatbots.config.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UUIDValidator implements ConstraintValidator<UUID, String> {


    @Override
    public void initialize(UUID constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // null values are valid
        if (value == null) {
            return true;
        }

        return value.matches(Constants.REGEX_UUID);
    }
}
