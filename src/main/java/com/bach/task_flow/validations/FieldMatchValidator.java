package com.bach.task_flow.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldValueMatch, Object> {

    String field;
    String fieldMatch;

    @Override
    public void initialize(FieldValueMatch constraintAnnotation) {

        field = constraintAnnotation.field();
        fieldMatch = constraintAnnotation.fieldMatch();

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        Object firstField = new BeanWrapperImpl(o).getPropertyValue(field);
        Object secondField = new BeanWrapperImpl(o).getPropertyValue(fieldMatch);
        return Objects.equals(firstField, secondField);

    }
}
