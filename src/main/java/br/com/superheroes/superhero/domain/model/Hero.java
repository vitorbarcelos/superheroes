package br.com.superheroes.superhero.domain.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public record Hero(
    Long id,
    String name,
    String heroName,
    LocalDate birthDate,
    Double height,
    Double weight,
    Set<Superpower> superpowers) {

  public Hero(
      Long id, String name, String heroName, LocalDate birthDate, Double height, Double weight) {
    this(id, name, heroName, birthDate, height, weight, new HashSet<>());
  }
}
