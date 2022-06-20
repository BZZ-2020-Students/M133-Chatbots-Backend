package dev.zwazel.chatbots.util.annotation;

import dev.zwazel.chatbots.config.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Text validator, for String.
 *
 * @author Zwazel
 * @see Text
 * @since 1.3.0
 */
public class TextValidatorString implements ConstraintValidator<Text, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // null values are not valid
        if (value == null) {
            return false;
        }

        // check if the length is correct
        if (value.length() > Constants.MAX_TEXT_LENGTH) {
            return false;
        }

        // Check if the String is empty or blank
        return !value.isEmpty() && !value.trim().isEmpty();
    }
}
