package dev.zwazel.chatbots.util.annotation;

import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UUIDValidator.class})
public @interface UUID {
    String message() default "Invalid UUID";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
