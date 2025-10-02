package br.com.superheroes.superhero.application.validation;

import java.util.Map;
import java.util.Optional;

public abstract class BaseValidator {
  private static final String DEFAULT_FIELD_NAME = "Atributo";
  private static final String DEFAULT_FIELD_KEY = "field";
  protected String failureMessage;
  protected String fieldName;

  protected String getDefaultFieldRegex() {
    return String.format("{%s}", DEFAULT_FIELD_KEY);
  }

  protected String getFieldName() {
    return Optional.ofNullable(fieldName).filter(s -> !s.isEmpty()).orElse(DEFAULT_FIELD_NAME);
  }

  protected String getFailureMessage() {
    return failureMessage.replace(getDefaultFieldRegex(), getFieldName());
  }

  protected String getFailureMessage(Map<String, String> placeholders) {
    String finalFieldName =
        Optional.ofNullable(placeholders.get(DEFAULT_FIELD_KEY))
            .filter(s -> !s.isEmpty())
            .orElse(getFieldName());

    String message = failureMessage.replace(getDefaultFieldRegex(), finalFieldName);

    for (Map.Entry<String, String> entry : placeholders.entrySet()) {
      if (!entry.getKey().equals(DEFAULT_FIELD_KEY)) {
        String key = String.format("{%s}", entry.getKey());
        message = message.replace(key, entry.getValue());
      }
    }

    return message;
  }
}
