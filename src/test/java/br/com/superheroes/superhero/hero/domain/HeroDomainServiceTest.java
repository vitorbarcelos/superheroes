package br.com.superheroes.superhero.hero.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.superheroes.superhero.hero.domain.exception.HeroNameConflictException;
import br.com.superheroes.superhero.hero.domain.exception.HeroNotFoundException;
import br.com.superheroes.superhero.hero.domain.model.Hero;
import br.com.superheroes.superhero.hero.domain.repository.HeroRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HeroDomainServiceTest {
  @InjectMocks private HeroDomainService service;
  @Mock private HeroRepository repository;
  private Hero batman;

  @BeforeEach
  void setUp() {
    batman = new Hero(1L, "Bruce Wayne", "Batman", LocalDate.of(1939, 5, 1), 1.88, 95.0, Set.of());
  }

  @Test
  void whenFindAll_shouldReturnAllHeroes() {
    when(repository.findAll()).thenReturn(List.of(batman));

    List<Hero> result = service.findAll();

    assertThat(result).containsExactly(batman);
    verify(repository).findAll();
  }

  @Test
  void whenFindById_shouldReturnHero() {
    when(repository.findById(1L)).thenReturn(Optional.of(batman));

    Hero result = service.findByIdOrThrow(1L);

    assertThat(result).isEqualTo(batman);
  }

  @Test
  void whenFindById_andNotExists_shouldThrow() {
    when(repository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.findByIdOrThrow(99L))
        .isInstanceOf(HeroNotFoundException.class)
        .hasMessageContaining("99");
  }

  @Test
  void whenExistsById_shouldNotThrow() {
    when(repository.existsById(1L)).thenReturn(true);

    service.existsByIdOrThrow(1L);

    verify(repository).existsById(1L);
  }

  @Test
  void whenExistsById_andNotExists_shouldThrow() {
    when(repository.existsById(1L)).thenReturn(false);

    assertThatThrownBy(() -> service.existsByIdOrThrow(1L))
        .isInstanceOf(HeroNotFoundException.class)
        .hasMessageContaining("1");
  }

  @Test
  void whenDeleteHeroById_shouldCallRepository() {
    when(repository.existsById(1L)).thenReturn(true);
    doNothing().when(repository).deleteById(1L);

    service.deleteHeroById(1L);

    verify(repository).deleteById(1L);
  }

  @Test
  void whenDeleteHeroById_andNotExists_shouldThrow() {
    when(repository.existsById(1L)).thenReturn(false);
    assertThatThrownBy(() -> service.deleteHeroById(1L)).isInstanceOf(HeroNotFoundException.class);
  }

  @Test
  void whenCreateHero_andNameNotExists_shouldSaveHero() {
    when(repository.findByHeroName("Batman")).thenReturn(Optional.empty());
    when(repository.save(batman)).thenReturn(batman);

    Hero result = service.createHero(batman);

    assertThat(result).isEqualTo(batman);
    verify(repository).save(batman);
  }

  @Test
  void whenCreateHero_andNameAlreadyExists_shouldThrow() {
    when(repository.findByHeroName("Batman")).thenReturn(Optional.of(batman));

    assertThatThrownBy(() -> service.createHero(batman))
        .isInstanceOf(HeroNameConflictException.class)
        .hasMessageContaining("Batman");

    verify(repository, never()).save(any());
  }

  @Test
  void whenUpdateHero_andValid_shouldSave() {
    Hero updated =
        new Hero(1L, "Bruce Wayne", "Batman", LocalDate.of(1939, 5, 1), 1.88, 96.0, Set.of());

    when(repository.existsById(1L)).thenReturn(true);
    when(repository.findByHeroNameAndIdNot("Batman", 1L)).thenReturn(Optional.empty());
    when(repository.save(updated)).thenReturn(updated);

    Hero result = service.updateHero(updated);

    assertThat(result).isEqualTo(updated);
    verify(repository).save(updated);
  }

  @Test
  void whenUpdateHero_andNotExists_shouldThrow() {
    when(repository.existsById(1L)).thenReturn(false);

    assertThatThrownBy(() -> service.updateHero(batman)).isInstanceOf(HeroNotFoundException.class);

    verify(repository, never()).save(any());
  }

  @Test
  void whenUpdateHero_andNameConflict_shouldThrow() {
    Hero other =
        new Hero(1L, "Bruce Wayne", "Batman", LocalDate.of(1939, 5, 1), 1.88, 95.0, Set.of());

    when(repository.existsById(1L)).thenReturn(true);
    when(repository.findByHeroNameAndIdNot("Batman", 1L)).thenReturn(Optional.of(other));

    assertThatThrownBy(() -> service.updateHero(batman))
        .isInstanceOf(HeroNameConflictException.class)
        .hasMessageContaining("Batman");

    verify(repository, never()).save(any());
  }
}
