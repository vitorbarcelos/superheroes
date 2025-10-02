package br.com.superheroes.superhero.domain.model;

import java.util.HashSet;
import java.util.Set;

public record Superpower(Long id, String name, String description, Set<Hero> heroes) {
  public Superpower(Long id, String name, String description) {
    this(id, name, description, new HashSet<>());
  }
}
