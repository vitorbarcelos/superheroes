package br.com.superheroes.superhero.shared.validation;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.superheroes.fixtures.BaseValidatorDummy;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseValidatorTest {
  private BaseValidatorDummy validator;

  @BeforeEach
  void setUp() {
    validator = new BaseValidatorDummy();
    validator.setFieldName("idade");
    validator.setFailureMessage("O campo {field} é obrigatório");
  }

  @Test
  void getDefaultFieldRegex_shouldReturnCorrectRegex() {
    assertThat(validator.getDefaultFieldRegex()).isEqualTo("{field}");
  }

  @Test
  void getFieldName_whenFieldNameIsSet_shouldReturnIt() {
    assertThat(validator.getFieldName()).isEqualTo("idade");
  }

  @Test
  void getFieldName_whenFieldNameIsNullOrEmpty_shouldReturnDefault() {
    validator.setFieldName(null);
    assertThat(validator.getFieldName()).isEqualTo("Atributo");

    validator.setFieldName("");
    assertThat(validator.getFieldName()).isEqualTo("Atributo");
  }

  @Test
  void getFailureMessage_withoutPlaceholders_shouldReplaceField() {
    assertThat(validator.getFailureMessage()).isEqualTo("O campo idade é obrigatório");
  }

  @Test
  void getFailureMessage_withPlaceholders_shouldReplaceAll() {
    Map<String, String> placeholders = Map.of("field", "idade", "min", "18");
    validator.setFailureMessage("O campo {field} deve ser maior que {min} anos");

    assertThat(validator.getFailureMessage(placeholders))
        .isEqualTo("O campo idade deve ser maior que 18 anos");
  }

  @Test
  void getFailureMessage_withMissingPlaceholders_shouldUseDefaults() {
    Map<String, String> placeholders = Map.of("min", "10");
    validator.setFailureMessage("O campo {field} deve ser maior que {min} anos");

    assertThat(validator.getFailureMessage(placeholders))
        .isEqualTo("O campo idade deve ser maior que 10 anos");
  }

  @Test
  void getFailureMessage_whenFieldPlaceholderIsEmpty_shouldUseDefaultFieldName() {
    Map<String, String> placeholders = Map.of("field", "");
    validator.setFailureMessage("O campo {field} é obrigatório");

    assertThat(validator.getFailureMessage(placeholders)).isEqualTo("O campo idade é obrigatório");
  }
}
