package br.com.superheroes.superhero.hero.application.dto;

import java.time.LocalDate;

public abstract class HeroDataDto {
  public abstract String getName();

  public abstract String getHeroName();

  public abstract Double getHeight();

  public abstract Double getWeight();

  public abstract LocalDate getBirthDate();
}
