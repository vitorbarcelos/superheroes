package br.com.superheroes.superhero.shared.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import br.com.superheroes.shared.validation.LocalDateValidator;
import br.com.superheroes.shared.validation.ValidDate;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Annotation;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LocalDateValidatorTest {

  @Mock private ConstraintValidatorContext mockContext;
  private LocalDateValidator validator;

  @BeforeEach
  void setUp() {
    validator = new LocalDateValidator();
  }

  @Test
  void whenValidStringDate_shouldReturnTrue() {
    validator.initialize(new ValidDateStub("yyyy-MM-dd", "Data inválida", "data"));

    boolean result = validator.isValid("2025-10-02", mockContext);

    assertThat(result).isTrue();
    verifyNoInteractions(mockContext);
  }

  @Test
  void whenValidLocalDate_shouldReturnTrue() {
    validator.initialize(new ValidDateStub("yyyy-MM-dd", "Data inválida", "data"));

    boolean result = validator.isValid(LocalDate.of(2025, 10, 2), mockContext);

    assertThat(result).isTrue();
    verifyNoInteractions(mockContext);
  }

  @Test
  void whenInvalidStringDate_shouldReturnFalseAndBuildViolation() {
    validator.initialize(new ValidDateStub("yyyy-MM-dd", "Data inválida para {field}", "data"));

    ConstraintValidatorContext.ConstraintViolationBuilder mockBuilder =
        mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
    when(mockContext.buildConstraintViolationWithTemplate("Data inválida para data"))
        .thenReturn(mockBuilder);

    boolean result = validator.isValid("invalid-date", mockContext);

    assertThat(result).isFalse();
    verify(mockContext).disableDefaultConstraintViolation();
    verify(mockContext).buildConstraintViolationWithTemplate("Data inválida para data");
    verify(mockBuilder).addConstraintViolation();
  }

  @Test
  void whenValueIsNull_shouldReturnTrue() {
    validator.initialize(new ValidDateStub("yyyy-MM-dd", "Data inválida", "data"));

    boolean result = validator.isValid(null, mockContext);

    assertThat(result).isTrue();
    verifyNoInteractions(mockContext);
  }

  @Test
  void whenValueIsUnsupportedType_shouldReturnFalseAndBuildViolation() {
    validator.initialize(new ValidDateStub("yyyy-MM-dd", "Data inválida para {field}", "data"));

    ConstraintValidatorContext.ConstraintViolationBuilder mockBuilder =
        mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
    when(mockContext.buildConstraintViolationWithTemplate("Data inválida para data"))
        .thenReturn(mockBuilder);

    boolean result = validator.isValid(new Object(), mockContext);

    assertThat(result).isFalse();
    verify(mockContext).disableDefaultConstraintViolation();
    verify(mockContext).buildConstraintViolationWithTemplate("Data inválida para data");
    verify(mockBuilder).addConstraintViolation();
  }

  private record ValidDateStub(String pattern, String message, String name) implements ValidDate {
    @Override
    public Class<? extends Annotation> annotationType() {
      return ValidDate.class;
    }

    @Override
    public Class<? extends Payload>[] payload() {
      return new Class[0];
    }

    @Override
    public Class<?>[] groups() {
      return new Class[0];
    }
  }
}
