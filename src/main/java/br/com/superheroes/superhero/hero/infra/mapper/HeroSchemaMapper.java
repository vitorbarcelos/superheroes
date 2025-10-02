package br.com.superheroes.superhero.hero.infra.mapper;

import br.com.superheroes.superhero.hero.domain.model.Hero;
import br.com.superheroes.superhero.hero.infra.schema.HeroSchema;
import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import br.com.superheroes.superhero.superpower.infra.schema.SuperpowerSchema;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HeroSchemaMapper {
  default List<Hero> toDomain(List<HeroSchema> schemas) {
    return schemas.stream().map(this::toDomain).collect(Collectors.toList());
  }

  default Hero toDomain(HeroSchema schema) {
    Long id = schema.getId();
    String name = schema.getName();
    Double height = schema.getHeight();
    Double weight = schema.getWeight();
    String heroName = schema.getHeroName();
    LocalDate birthDate = schema.getBirthDate();

    Set<Superpower> superpowers =
        schema.getSuperpowers().stream()
            .map(
                superpowerSchema -> {
                  Long superpowerId = superpowerSchema.getId();
                  String superpowerName = superpowerSchema.getName();
                  String description = superpowerSchema.getDescription();
                  return new Superpower(superpowerId, superpowerName, description, null);
                })
            .collect(Collectors.toSet());

    return new Hero(id, name, heroName, birthDate, height, weight, superpowers);
  }

  default HeroSchema toSchema(Hero hero) {
    Long id = hero.id();
    String name = hero.name();
    Double height = hero.height();
    Double weight = hero.weight();
    String heroName = hero.heroName();
    LocalDate birthDate = hero.birthDate();

    Set<SuperpowerSchema> superpowers =
        hero.superpowers().stream()
            .map(
                superpower -> {
                  Long superpowerId = superpower.id();
                  String superpowerName = superpower.name();
                  String description = superpower.description();
                  return new SuperpowerSchema(superpowerId, superpowerName, description, null);
                })
            .collect(Collectors.toSet());

    return new HeroSchema(id, name, heroName, birthDate, height, weight, superpowers);
  }
}
