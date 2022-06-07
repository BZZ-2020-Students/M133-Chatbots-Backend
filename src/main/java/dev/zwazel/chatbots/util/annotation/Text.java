package dev.zwazel.chatbots.util.annotation;

import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {TextValidator.class})
public @interface Text {
    String message() default "Invalid Text";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
