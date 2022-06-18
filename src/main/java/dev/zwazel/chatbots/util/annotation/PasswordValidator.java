package dev.zwazel.chatbots.util.annotation;

import dev.zwazel.chatbots.config.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // null values are valid
        if (value == null) {
            return true;
        }

        // Checks for min and max characters, at least one uppercase letter, one lowercase letter, one number and one special character:
        String specialChars = "!@#$%^&*()_+{}|:\"<>?`~\\-=\\[\\];',./äöüèàéìòùÀÉÌÒÙ";
        return value.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[" + specialChars + "])" +
                "[A-Za-z\\d" + specialChars + "]{" +
                Constants.MIN_PASSWORD_LENGTH + "," +
                Constants.MAX_PASSWORD_LENGTH + "}$");
    }
}
