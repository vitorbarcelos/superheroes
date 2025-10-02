package br.com.superheroes.superhero.superpower.domain.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class SuperpowerNotFoundExceptionTest {
  @Test
  void whenConstructedWithSingleId_shouldSetCorrectMessage() {
    SuperpowerNotFoundException ex = new SuperpowerNotFoundException(42L);
    assertThat(ex).hasMessage("Superpoder com id '42' não encontrado");
  }

  @Test
  void whenConstructedWithMultipleIds_shouldSetCorrectMessage() {
    SuperpowerNotFoundException ex = new SuperpowerNotFoundException(List.of(1L, 2L, 3L));
    assertThat(ex).hasMessage("Superpoder(es) não encontrado(s) para ID(s): [1, 2, 3]");
  }
}
