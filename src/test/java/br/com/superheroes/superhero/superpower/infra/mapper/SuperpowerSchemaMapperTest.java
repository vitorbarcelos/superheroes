package br.com.superheroes.superhero.superpower.infra.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.superheroes.superhero.hero.domain.model.Hero;
import br.com.superheroes.superhero.hero.infra.schema.HeroSchema;
import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import br.com.superheroes.superhero.superpower.infra.schema.SuperpowerSchema;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SuperpowerSchemaMapperTest {
  private SuperpowerSchemaMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new SuperpowerSchemaMapper() {};
  }

  @Test
  void toDomain_shouldMapSchemaToDomainWithHeroes() {
    HeroSchema heroSchema =
        new HeroSchema(1L, "Bruce Wayne", "Batman", LocalDate.of(1939, 5, 1), 1.88, 95.0, null);
    SuperpowerSchema schema =
        new SuperpowerSchema(1L, "Inteligência", "Alta inteligência", Set.of(heroSchema));

    Superpower domain = mapper.toDomain(schema);

    assertThat(domain.id()).isEqualTo(1L);
    assertThat(domain.name()).isEqualTo("Inteligência");
    assertThat(domain.description()).isEqualTo("Alta inteligência");
    assertThat(domain.heroes()).hasSize(1);

    Hero hero = domain.heroes().iterator().next();
    assertThat(hero.id()).isEqualTo(1L);
    assertThat(hero.name()).isEqualTo("Bruce Wayne");
    assertThat(hero.heroName()).isEqualTo("Batman");
    assertThat(hero.height()).isEqualTo(1.88);
    assertThat(hero.weight()).isEqualTo(95.0);
    assertThat(hero.birthDate()).isEqualTo(LocalDate.of(1939, 5, 1));
  }

  @Test
  void toDomain_shouldMapListOfSchemas() {
    SuperpowerSchema schema1 =
        new SuperpowerSchema(1L, "Inteligência", "Alta inteligência", Set.of());
    SuperpowerSchema schema2 = new SuperpowerSchema(2L, "Força", "Super força", Set.of());
    List<SuperpowerSchema> schemas = List.of(schema1, schema2);

    List<Superpower> domains = mapper.toDomain(schemas);

    assertThat(domains).hasSize(2);
    assertThat(domains.get(0).id()).isEqualTo(1L);
    assertThat(domains.get(1).id()).isEqualTo(2L);
  }

  @Test
  void toSchema_shouldMapDomainToSchemaWithHeroes() {
    Hero hero = new Hero(1L, "Bruce Wayne", "Batman", LocalDate.of(1939, 5, 1), 1.88, 95.0, null);
    Superpower domain = new Superpower(1L, "Inteligência", "Alta inteligência", Set.of(hero));

    SuperpowerSchema schema = mapper.toSchema(domain);

    assertThat(schema.getId()).isEqualTo(1L);
    assertThat(schema.getName()).isEqualTo("Inteligência");
    assertThat(schema.getDescription()).isEqualTo("Alta inteligência");
    assertThat(schema.getHeroes()).hasSize(1);

    HeroSchema heroSchema = schema.getHeroes().iterator().next();
    assertThat(heroSchema.getId()).isEqualTo(1L);
    assertThat(heroSchema.getName()).isEqualTo("Bruce Wayne");
    assertThat(heroSchema.getHeroName()).isEqualTo("Batman");
    assertThat(heroSchema.getHeight()).isEqualTo(1.88);
    assertThat(heroSchema.getWeight()).isEqualTo(95.0);
    assertThat(heroSchema.getBirthDate()).isEqualTo(LocalDate.of(1939, 5, 1));
  }

  @Test
  void toSchema_shouldHandleEmptyHeroesSet() {
    Superpower domain = new Superpower(1L, "Força", "Super força", Set.of());

    SuperpowerSchema schema = mapper.toSchema(domain);

    assertThat(schema.getHeroes()).isEmpty();
    assertThat(schema.getId()).isEqualTo(1L);
    assertThat(schema.getName()).isEqualTo("Força");
    assertThat(schema.getDescription()).isEqualTo("Super força");
  }
}
