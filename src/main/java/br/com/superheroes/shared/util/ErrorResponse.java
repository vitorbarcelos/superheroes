package br.com.superheroes.shared.util;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
  private int status;
  private String path;
  private String error;
  private LocalDateTime timestamp;
  private List<Map<String, String>> errors = new LinkedList<>();

  public void addError(String field, String message) {
    var errorMap = Map.of("field", field, "message", message);
    this.errors.add(errorMap);
  }

  public void addError(String message) {
    var errorMap = Map.of("message", message);
    this.errors.add(errorMap);
  }

  public Map<String, Object> toBody() {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", timestamp);
    body.put("path", path);
    body.put("status", status);
    body.put("error", error);
    body.put("errors", errors);
    return body;
  }
}
