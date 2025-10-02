package br.com.superheroes.shared.exception;

import br.com.superheroes.shared.util.ErrorResponse;
import br.com.superheroes.superhero.hero.domain.exception.HeroNameConflictException;
import br.com.superheroes.superhero.superpower.domain.exception.InvalidSuperpowerException;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private String getPath(WebRequest request) {
    return request.getDescription(false).replace("uri=", "").trim();
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Map<String, Object>> handleMethodNotAllowed(
      HttpRequestMethodNotSupportedException e, WebRequest request) {

    ErrorResponse response = new ErrorResponse();
    response.setTimestamp(LocalDateTime.now());
    response.setPath(getPath(request));
    response.setError(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
    response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
    response.addError("Método HTTP não suportado: " + e.getMethod());

    return ResponseEntity.status(response.getStatus()).body(response.toBody());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(
      MethodArgumentNotValidException e, WebRequest request) {

    ErrorResponse response = new ErrorResponse();
    response.setTimestamp(LocalDateTime.now());
    response.setPath(getPath(request));
    response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
    response.setStatus(HttpStatus.BAD_REQUEST.value());

    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      response.addError(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return ResponseEntity.status(response.getStatus()).body(response.toBody());
  }

  @ExceptionHandler(SuperHeroException.class)
  public ResponseEntity<Map<String, Object>> handleSuperHeroException(
      SuperHeroException e, WebRequest request) {

    HttpStatus status = HttpStatus.NOT_FOUND;

    if (e instanceof HeroNameConflictException) {
      status = HttpStatus.CONFLICT;
    }

    if (e instanceof InvalidSuperpowerException) {
      status = HttpStatus.BAD_REQUEST;
    }

    ErrorResponse response = new ErrorResponse();
    response.setTimestamp(LocalDateTime.now());
    response.setPath(getPath(request));
    response.setError(status.getReasonPhrase());
    response.setStatus(status.value());
    response.addError(e.getMessage());

    return ResponseEntity.status(response.getStatus()).body(response.toBody());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGenericException(
      Exception e, WebRequest request) {

    ErrorResponse response = new ErrorResponse();
    response.setTimestamp(LocalDateTime.now());
    response.setPath(getPath(request));
    response.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.addError(e.getMessage());

    return ResponseEntity.status(response.getStatus()).body(response.toBody());
  }
}
