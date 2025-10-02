package br.com.superheroes.superhero.shared.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.superheroes.shared.exception.GlobalExceptionHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
  private GlobalExceptionHandler handler;
  @Mock private WebRequest mockRequest;

  @BeforeEach
  void setUp() {
    handler = new GlobalExceptionHandler();
  }

  @Test
  void whenGenericExceptionOccurs_shouldReturnInternalServerErrorResponse() {
    Exception exception = new Exception("Something went wrong");
    when(mockRequest.getDescription(false)).thenReturn("uri=/test/path");

    var responseEntity = handler.handleGenericException(exception, mockRequest);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

    Map<String, Object> body = responseEntity.getBody();
    assertThat(body).isNotNull();
    assertThat(body.get("status")).isEqualTo(500);
    assertThat(body.get("error")).isEqualTo("Internal Server Error");
    assertThat(body.get("path")).isEqualTo("/test/path");
    assertThat(body.get("timestamp")).isNotNull();

    @SuppressWarnings("unchecked")
    List<Map<String, String>> errors = (List<Map<String, String>>) body.get("errors");
    assertThat(errors).hasSize(1);
    assertThat(errors.getFirst().get("message")).isEqualTo("Something went wrong");
  }
}
