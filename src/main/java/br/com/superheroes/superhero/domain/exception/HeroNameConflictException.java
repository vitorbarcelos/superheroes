package br.com.superheroes.superhero.domain.exception;

public class HeroNameConflictException extends SuperHeroException {
  public HeroNameConflictException(String heroName) {
    super("Já existe um herói com o nome '" + heroName + "'");
  }
}
