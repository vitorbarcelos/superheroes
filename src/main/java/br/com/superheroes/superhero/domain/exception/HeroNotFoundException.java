package br.com.superheroes.superhero.domain.exception;

public class HeroNotFoundException extends SuperHeroException {
  public HeroNotFoundException(Long id) {
    super("Herói com id '" + id + "' não encontrado");
  }

  public HeroNotFoundException(String heroName) {
    super("Herói com nome '" + heroName + "' não encontrado");
  }
}
