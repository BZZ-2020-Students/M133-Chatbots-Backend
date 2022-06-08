package dev.zwazel.chatbots.util.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TextValidator implements ConstraintValidator<Text, dev.zwazel.chatbots.classes.model.Text> {
    @Override
    public boolean isValid(dev.zwazel.chatbots.classes.model.Text value, ConstraintValidatorContext context) {
        return new TextValidatorString().isValid(value.getText(), context);
    }
}
