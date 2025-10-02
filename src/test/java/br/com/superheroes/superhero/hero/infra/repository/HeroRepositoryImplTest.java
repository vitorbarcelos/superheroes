package br.com.superheroes.superhero.hero.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import br.com.superheroes.superhero.hero.domain.model.Hero;
import br.com.superheroes.superhero.hero.infra.mapper.HeroSchemaMapper;
import br.com.superheroes.superhero.hero.infra.schema.HeroSchema;
import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import br.com.superheroes.superhero.superpower.infra.schema.SuperpowerSchema;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HeroRepositoryImplTest {
  @InjectMocks private HeroRepositoryImpl repository;
  @Mock private SpringHeroRepository springRepository;
  @Mock private HeroSchemaMapper mapper;
  private HeroSchema batmanSchema;
  private Hero batman;

  @BeforeEach
  void setUp() {
    batman =
        new Hero(
            1L,
            "Bruce Wayne",
            "Batman",
            LocalDate.of(1939, 5, 1),
            1.88,
            95.0,
            Set.of(new Superpower(1L, "Inteligência", "Capacidade intelectual", null)));

    batmanSchema =
        new HeroSchema(
            1L,
            "Bruce Wayne",
            "Batman",
            LocalDate.of(1939, 5, 1),
            1.88,
            95.0,
            Set.of(new SuperpowerSchema(1L, "Inteligência", "Capacidade intelectual", null)));
  }

  @Test
  void whenSave_shouldReturnSavedHero() {
    when(mapper.toSchema(batman)).thenReturn(batmanSchema);
    when(springRepository.save(batmanSchema)).thenReturn(batmanSchema);
    when(mapper.toDomain(batmanSchema)).thenReturn(batman);

    Hero result = repository.save(batman);

    assertThat(result).isEqualTo(batman);
    verify(springRepository).save(batmanSchema);
  }

  @Test
  void whenFindAll_shouldReturnAllHeroes() {
    when(springRepository.findAll()).thenReturn(List.of(batmanSchema));
    when(mapper.toDomain(List.of(batmanSchema))).thenReturn(List.of(batman));

    List<Hero> result = repository.findAll();

    assertThat(result).containsExactly(batman);
    verify(springRepository).findAll();
  }

  @Test
  void whenDeleteById_shouldCallSpringRepository() {
    doNothing().when(springRepository).deleteById(1L);

    repository.deleteById(1L);

    verify(springRepository).deleteById(1L);
  }

  @Test
  void whenExistsById_shouldReturnTrue() {
    when(springRepository.existsById(1L)).thenReturn(true);

    boolean result = repository.existsById(1L);

    assertThat(result).isTrue();
    verify(springRepository).existsById(1L);
  }

  @Test
  void whenFindById_shouldReturnHero() {
    when(springRepository.findById(1L)).thenReturn(Optional.of(batmanSchema));
    when(mapper.toDomain(batmanSchema)).thenReturn(batman);

    Optional<Hero> result = repository.findById(1L);

    assertThat(result).contains(batman);
    verify(springRepository).findById(1L);
  }

  @Test
  void whenFindByHeroName_shouldReturnHero() {
    when(springRepository.findByHeroName("Batman")).thenReturn(Optional.of(batmanSchema));
    when(mapper.toDomain(batmanSchema)).thenReturn(batman);

    Optional<Hero> result = repository.findByHeroName("Batman");

    assertThat(result).contains(batman);
    verify(springRepository).findByHeroName("Batman");
  }

  @Test
  void whenFindByHeroNameAndIdNot_shouldReturnHero() {
    when(springRepository.findByHeroNameAndIdNot("Batman", 2L))
        .thenReturn(Optional.of(batmanSchema));
    when(mapper.toDomain(batmanSchema)).thenReturn(batman);

    Optional<Hero> result = repository.findByHeroNameAndIdNot("Batman", 2L);

    assertThat(result).contains(batman);
    verify(springRepository).findByHeroNameAndIdNot("Batman", 2L);
  }
}
