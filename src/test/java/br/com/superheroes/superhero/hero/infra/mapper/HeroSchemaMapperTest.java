package br.com.superheroes.superhero.hero.infra.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

import br.com.superheroes.superhero.hero.domain.model.Hero;
import br.com.superheroes.superhero.hero.infra.schema.HeroSchema;
import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import br.com.superheroes.superhero.superpower.infra.schema.SuperpowerSchema;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeroSchemaMapperTest {
  private HeroSchemaMapper mapper;
  private HeroSchema batmanSchema;
  private Hero batmanDomain;

  @BeforeEach
  void setUp() {
    mapper = new HeroSchemaMapper() {};

    SuperpowerSchema superpowerSchema =
        new SuperpowerSchema(1L, "Inteligência", "Capacidade intelectual", null);
    batmanSchema =
        new HeroSchema(
            1L,
            "Bruce Wayne",
            "Batman",
            LocalDate.of(1939, 5, 1),
            1.88,
            95.0,
            Set.of(superpowerSchema));

    Superpower superpowerDomain =
        new Superpower(1L, "Inteligência", "Capacidade intelectual", null);
    batmanDomain =
        new Hero(
            1L,
            "Bruce Wayne",
            "Batman",
            LocalDate.of(1939, 5, 1),
            1.88,
            95.0,
            Set.of(superpowerDomain));
  }

  @Test
  void whenToDomain_shouldMapCorrectly() {
    Hero result = mapper.toDomain(batmanSchema);

    assertThat(result.id()).isEqualTo(batmanSchema.getId());
    assertThat(result.name()).isEqualTo(batmanSchema.getName());
    assertThat(result.heroName()).isEqualTo(batmanSchema.getHeroName());
    assertThat(result.birthDate()).isEqualTo(batmanSchema.getBirthDate());
    assertThat(result.height()).isEqualTo(batmanSchema.getHeight());
    assertThat(result.weight()).isEqualTo(batmanSchema.getWeight());
    assertThat(result.superpowers())
        .extracting(Superpower::id, Superpower::name, Superpower::description)
        .containsExactlyInAnyOrder(tuple(1L, "Inteligência", "Capacidade intelectual"));
  }

  @Test
  void whenToSchema_shouldMapCorrectly() {
    HeroSchema result = mapper.toSchema(batmanDomain);

    assertThat(result.getId()).isEqualTo(batmanDomain.id());
    assertThat(result.getName()).isEqualTo(batmanDomain.name());
    assertThat(result.getHeroName()).isEqualTo(batmanDomain.heroName());
    assertThat(result.getBirthDate()).isEqualTo(batmanDomain.birthDate());
    assertThat(result.getHeight()).isEqualTo(batmanDomain.height());
    assertThat(result.getWeight()).isEqualTo(batmanDomain.weight());
    assertThat(result.getSuperpowers())
        .extracting(
            SuperpowerSchema::getId, SuperpowerSchema::getName, SuperpowerSchema::getDescription)
        .containsExactlyInAnyOrder(tuple(1L, "Inteligência", "Capacidade intelectual"));
  }

  @Test
  void whenToDomainList_shouldMapCorrectly() {
    List<Hero> result = mapper.toDomain(List.of(batmanSchema));

    assertThat(result).hasSize(1);
    assertThat(result.getFirst().id()).isEqualTo(batmanSchema.getId());
    assertThat(result.getFirst().name()).isEqualTo(batmanSchema.getName());
  }
}
