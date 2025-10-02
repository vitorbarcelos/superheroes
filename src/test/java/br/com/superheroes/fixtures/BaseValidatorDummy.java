package br.com.superheroes.fixtures;

import br.com.superheroes.shared.validation.BaseValidator;
import java.util.Map;

public class BaseValidatorDummy extends BaseValidator {
  @Override
  public String getDefaultFieldRegex() {
    return super.getDefaultFieldRegex();
  }

  @Override
  public String getFieldName() {
    return super.getFieldName();
  }

  public void setFieldName(String fieldName) {
      this.fieldName = fieldName;
  }

  @Override
  public String getFailureMessage() {
    return super.getFailureMessage();
  }

  public void setFailureMessage(String failureMessage) {
      this.failureMessage = failureMessage;
  }

  @Override
  public String getFailureMessage(Map<String, String> placeholders) {
    return super.getFailureMessage(placeholders);
  }
}
