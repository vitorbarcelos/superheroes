package br.com.superheroes.superhero.hero.domain.exception;

import br.com.superheroes.shared.exception.SuperHeroException;

public class HeroNameConflictException extends SuperHeroException {
  public HeroNameConflictException(String heroName) {
    super("Já existe um herói com o nome '" + heroName + "'");
  }
}
