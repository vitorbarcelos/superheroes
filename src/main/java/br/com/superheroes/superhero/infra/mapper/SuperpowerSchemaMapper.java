package br.com.superheroes.superhero.infra.mapper;

import br.com.superheroes.superhero.domain.model.Hero;
import br.com.superheroes.superhero.domain.model.Superpower;
import br.com.superheroes.superhero.infra.schema.HeroSchema;
import br.com.superheroes.superhero.infra.schema.SuperpowerSchema;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuperpowerSchemaMapper {
  default List<Superpower> toDomain(List<SuperpowerSchema> schemas) {
    return schemas.stream().map(this::toDomain).collect(Collectors.toList());
  }

  default Superpower toDomain(SuperpowerSchema schema) {
    Long id = schema.getId();
    String name = schema.getName();
    String description = schema.getDescription();

    Set<Hero> heroes =
        schema.getHeroes().stream()
            .map(
                heroSchema -> {
                  Long heroId = heroSchema.getId();
                  String heroName = heroSchema.getName();
                  Double height = heroSchema.getHeight();
                  Double weight = heroSchema.getWeight();
                  String heroHeroName = heroSchema.getHeroName();
                  LocalDate birthDate = heroSchema.getBirthDate();
                  return new Hero(heroId, heroName, heroHeroName, birthDate, height, weight, null);
                })
            .collect(Collectors.toSet());

    return new Superpower(id, name, description, heroes);
  }

  default SuperpowerSchema toSchema(Superpower superpower) {
    Long id = superpower.id();
    String name = superpower.name();
    String description = superpower.description();

    Set<HeroSchema> heroes =
        superpower.heroes().stream()
            .map(
                hero -> {
                  Long heroId = hero.id();
                  String heroName = hero.name();
                  Double height = hero.height();
                  Double weight = hero.weight();
                  String heroHeroName = hero.heroName();
                  LocalDate birthDate = hero.birthDate();
                  return new HeroSchema(
                      heroId, heroName, heroHeroName, birthDate, height, weight, null);
                })
            .collect(Collectors.toSet());

    return new SuperpowerSchema(id, name, description, heroes);
  }
}
