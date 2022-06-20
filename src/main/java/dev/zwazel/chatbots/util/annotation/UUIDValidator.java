package dev.zwazel.chatbots.util.annotation;

import dev.zwazel.chatbots.config.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * UUID validator.
 *
 * @author Zwazel
 * @see UUID
 * @since 1.3.0
 */
public class UUIDValidator implements ConstraintValidator<UUID, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // null values are valid
        if (value == null) {
            return true;
        }

        // check if the length is correct
        if (value.length() != Constants.UUID_LENGTH) {
            return false;
        }

        // Check if the string matches the regex
        return value.matches("^[\\da-f]{8}-[\\da-f]{4}-[\\da-f]{4}-[\\da-f]{4}-[\\da-f]{12}$");
    }
}
