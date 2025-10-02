package br.com.superheroes.superhero.hero.application.mapper;

import br.com.superheroes.superhero.hero.application.dto.HeroDataDto;
import br.com.superheroes.superhero.hero.application.dto.HeroOutputDto;
import br.com.superheroes.superhero.hero.domain.model.Hero;
import br.com.superheroes.superhero.superpower.application.dto.SuperpowerOutputDto;
import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HeroMapper {
  default List<HeroOutputDto> toOutput(List<Hero> heroes) {
    return heroes.stream().map(this::toOutput).collect(Collectors.toList());
  }

  default HeroOutputDto toOutput(Hero hero) {
    Long id = hero.id();
    String name = hero.name();
    Double weight = hero.weight();
    Double height = hero.height();
    String heroName = hero.heroName();
    LocalDate birthDate = hero.birthDate();
    Set<SuperpowerOutputDto> superpowers =
        hero.superpowers().stream()
            .map(
                superpower ->
                    new SuperpowerOutputDto(
                        superpower.id(), superpower.name(), superpower.description()))
            .collect(Collectors.toSet());

    return new HeroOutputDto(id, name, height, weight, heroName, birthDate, superpowers);
  }

  default Hero toDomain(HeroDataDto dto, Set<Superpower> superpowers) {
    return toDomain(null, dto, superpowers);
  }

  default Hero toDomain(Long id, HeroDataDto dto, Set<Superpower> superpowers) {
    String name = dto.getName();
    Double height = dto.getHeight();
    Double weight = dto.getWeight();
    String heroName = dto.getHeroName();
    LocalDate birthDate = dto.getBirthDate();
    return new Hero(id, name, heroName, birthDate, height, weight, superpowers);
  }
}
