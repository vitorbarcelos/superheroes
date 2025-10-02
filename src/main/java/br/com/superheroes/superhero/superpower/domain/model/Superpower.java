package br.com.superheroes.superhero.superpower.domain.model;

import br.com.superheroes.superhero.hero.domain.model.Hero;
import java.util.HashSet;
import java.util.Set;

public record Superpower(Long id, String name, String description, Set<Hero> heroes) {
  public Superpower(Long id, String name, String description) {
    this(id, name, description, new HashSet<>());
  }
}
