package br.com.superheroes.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateValidator extends BaseValidator
    implements ConstraintValidator<ValidDate, Object> {
  private String pattern;

  @Override
  public void initialize(ValidDate annotation) {
    failureMessage = annotation.message();
    pattern = annotation.pattern();
    fieldName = annotation.name();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if (value == null) return true;

    boolean isValid = parseValue(value) != null;

    if (!isValid) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(getFailureMessage()).addConstraintViolation();
    }

    return isValid;
  }

  private LocalDate parseValue(Object value) {
    try {
      if (value instanceof String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(s, formatter);
      } else if (value instanceof LocalDate d) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(d.format(formatter), formatter);
      }
    } catch (DateTimeParseException e) {
      return null;
    }
    return null;
  }
}
