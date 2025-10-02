package br.com.superheroes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SuperHeroesApplicationTest {
  @Test
  void whenMainIsCalled_thenNoExceptionThrown() {
    String[] args = {};
    assertDoesNotThrow(() -> SuperHeroesApplication.main(args));
  }
}
