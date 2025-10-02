package br.com.superheroes.superhero.hero.domain.exception;

import br.com.superheroes.shared.exception.SuperHeroException;

public class HeroNotFoundException extends SuperHeroException {
  public HeroNotFoundException(Long id) {
    super("Herói com id '" + id + "' não encontrado");
  }
}
