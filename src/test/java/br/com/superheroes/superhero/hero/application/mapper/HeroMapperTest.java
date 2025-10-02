package br.com.superheroes.superhero.hero.application.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

import br.com.superheroes.superhero.hero.application.dto.HeroDataDto;
import br.com.superheroes.superhero.hero.application.dto.HeroOutputDto;
import br.com.superheroes.superhero.hero.domain.model.Hero;
import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeroMapperTest {
  private Superpower intelligence;
  private HeroDataDto batmanDto;
  private HeroMapper mapper;
  private Hero batman;

  @BeforeEach
  void setUp() {
    mapper = new HeroMapper() {};

    intelligence = new Superpower(1L, "Inteligência", "Capacidade intelectual");
    batman =
        new Hero(
            1L,
            "Bruce Wayne",
            "Batman",
            LocalDate.of(1939, 5, 1),
            1.88,
            95.0,
            Set.of(intelligence));

    batmanDto =
        new HeroDataDto() {
          @Override
          public String getName() {
            return "Bruce Wayne";
          }

          @Override
          public String getHeroName() {
            return "Batman";
          }

          @Override
          public Double getHeight() {
            return 1.88;
          }

          @Override
          public Double getWeight() {
            return 95.0;
          }

          @Override
          public LocalDate getBirthDate() {
            return LocalDate.of(1939, 5, 1);
          }
        };
  }

  @Test
  void whenToOutput_shouldMapHeroToDto() {
    HeroOutputDto dto = mapper.toOutput(batman);

    assertThat(dto.getId()).isEqualTo(batman.id());
    assertThat(dto.getName()).isEqualTo(batman.name());
    assertThat(dto.getHeroName()).isEqualTo(batman.heroName());
    assertThat(dto.getHeight()).isEqualTo(batman.height());
    assertThat(dto.getWeight()).isEqualTo(batman.weight());
    assertThat(dto.getBirthDate()).isEqualTo(batman.birthDate());
    assertThat(dto.getSuperpowers())
        .extracting("id", "name", "description")
        .containsExactlyInAnyOrder(
            tuple(intelligence.id(), intelligence.name(), intelligence.description()));
  }

  @Test
  void whenToOutputList_shouldMapListOfHeroes() {
    List<HeroOutputDto> dtos = mapper.toOutput(List.of(batman));

    assertThat(dtos).hasSize(1);
    assertThat(dtos.getFirst().getId()).isEqualTo(batman.id());
  }

  @Test
  void whenToDomainWithoutId_shouldCreateHero() {
    Hero result = mapper.toDomain(batmanDto, Set.of(intelligence));

    assertThat(result.id()).isNull();
    assertThat(result.name()).isEqualTo(batmanDto.getName());
    assertThat(result.heroName()).isEqualTo(batmanDto.getHeroName());
    assertThat(result.height()).isEqualTo(batmanDto.getHeight());
    assertThat(result.weight()).isEqualTo(batmanDto.getWeight());
    assertThat(result.birthDate()).isEqualTo(batmanDto.getBirthDate());
    assertThat(result.superpowers()).containsExactlyInAnyOrder(intelligence);
  }

  @Test
  void whenToDomainWithId_shouldCreateHeroWithId() {
    Hero result = mapper.toDomain(1L, batmanDto, Set.of(intelligence));

    assertThat(result.id()).isEqualTo(1L);
    assertThat(result.name()).isEqualTo(batmanDto.getName());
    assertThat(result.heroName()).isEqualTo(batmanDto.getHeroName());
    assertThat(result.height()).isEqualTo(batmanDto.getHeight());
    assertThat(result.weight()).isEqualTo(batmanDto.getWeight());
    assertThat(result.birthDate()).isEqualTo(batmanDto.getBirthDate());
    assertThat(result.superpowers()).containsExactlyInAnyOrder(intelligence);
  }
}
