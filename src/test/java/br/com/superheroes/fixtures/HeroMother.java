package br.com.superheroes.fixtures;

import br.com.superheroes.superhero.hero.application.dto.CreateHeroDto;
import br.com.superheroes.superhero.hero.application.dto.HeroOutputDto;
import br.com.superheroes.superhero.hero.application.dto.UpdateHeroDto;
import br.com.superheroes.superhero.superpower.application.dto.SuperpowerOutputDto;
import java.time.LocalDate;
import java.util.Set;

public class HeroMother {
  public static final LocalDate BATMAN_BIRTH_DATE = LocalDate.of(1939, 5, 1);
  public static final Set<Long> BATMAN_SUPERPOWER_IDS = Set.of(1L, 2L);
  public static final String BATMAN_NAME = "Bruce Wayne";
  public static final String BATMAN_ALIAS = "Batman";
  public static final Double BATMAN_HEIGHT = 1.88;
  public static final Double BATMAN_WEIGHT = 95.0;
  public static final Long BATMAN_ID = 1L;

  public static final LocalDate SUPERMAN_BIRTH_DATE = LocalDate.of(1938, 4, 18);
  public static final Set<Long> SUPERMAN_SUPERPOWER_IDS = Set.of(3L, 4L, 5L);
  public static final String SUPERMAN_NAME = "Clark Kent";
  public static final String SUPERMAN_ALIAS = "Superman";
  public static final Double SUPERMAN_HEIGHT = 1.91;
  public static final Double SUPERMAN_WEIGHT = 107.0;
  public static final Long SUPERMAN_ID = 2L;

  public static final LocalDate SPIDERMAN_BIRTH_DATE = LocalDate.of(1962, 8, 1);
  public static final Set<Long> SPIDERMAN_SUPERPOWER_IDS = Set.of(6L);
  public static final String SPIDERMAN_NAME = "Peter Parker";
  public static final String SPIDERMAN_ALIAS = "Spider-Man";
  public static final Double SPIDERMAN_HEIGHT = 1.78;
  public static final Double SPIDERMAN_WEIGHT = 76.0;
  public static final Long SPIDERMAN_ID = 3L;

  public static CreateHeroDto getBatmanCreateHeroDto() {
    return new CreateHeroDto(
        BATMAN_NAME,
        BATMAN_ALIAS,
        BATMAN_HEIGHT,
        BATMAN_WEIGHT,
        BATMAN_BIRTH_DATE,
        BATMAN_SUPERPOWER_IDS);
  }

  public static UpdateHeroDto getBatmanUpdateHeroDto() {
    return new UpdateHeroDto(
        BATMAN_NAME, BATMAN_ALIAS, BATMAN_HEIGHT, 96.0, BATMAN_BIRTH_DATE, BATMAN_SUPERPOWER_IDS);
  }

  public static HeroOutputDto getBatmanCreatedHeroOutputDto(Set<SuperpowerOutputDto> superpowers) {
    return new HeroOutputDto(
        BATMAN_ID,
        BATMAN_NAME,
        BATMAN_HEIGHT,
        BATMAN_WEIGHT,
        BATMAN_ALIAS,
        BATMAN_BIRTH_DATE,
        superpowers);
  }

  public static HeroOutputDto getBatmanUpdatedHeroOutputDto(Set<SuperpowerOutputDto> superpowers) {
    return new HeroOutputDto(
        BATMAN_ID, BATMAN_NAME, BATMAN_HEIGHT, 96.0, BATMAN_ALIAS, BATMAN_BIRTH_DATE, superpowers);
  }

  public static HeroOutputDto getSupermanCreatedHeroOutputDto(
      Set<SuperpowerOutputDto> superpowers) {
    return new HeroOutputDto(
        SUPERMAN_ID,
        SUPERMAN_NAME,
        SUPERMAN_HEIGHT,
        SUPERMAN_WEIGHT,
        SUPERMAN_ALIAS,
        SUPERMAN_BIRTH_DATE,
        superpowers);
  }

  public static CreateHeroDto getSpidermanCreateHeroDto() {
    return new CreateHeroDto(
        SPIDERMAN_NAME,
        SPIDERMAN_ALIAS,
        SPIDERMAN_HEIGHT,
        SPIDERMAN_WEIGHT,
        SPIDERMAN_BIRTH_DATE,
        SPIDERMAN_SUPERPOWER_IDS);
  }

  public static HeroOutputDto getSpidermanCreatedHeroOutputDto(
      Set<SuperpowerOutputDto> superpowers) {
    return new HeroOutputDto(
        SPIDERMAN_ID,
        SPIDERMAN_NAME,
        SPIDERMAN_HEIGHT,
        SPIDERMAN_WEIGHT,
        SPIDERMAN_ALIAS,
        SPIDERMAN_BIRTH_DATE,
        superpowers);
  }
}
