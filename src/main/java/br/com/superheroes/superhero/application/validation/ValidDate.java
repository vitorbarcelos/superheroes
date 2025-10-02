package br.com.superheroes.superhero.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LocalDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
  String message() default "O {field} possui um formato de data inválido.";

  Class<? extends Payload>[] payload() default {};

  Class<?>[] groups() default {};

  String name() default "";

  String pattern();
}
