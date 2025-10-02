package br.com.superheroes.superhero.hero.domain.model;

import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import java.time.LocalDate;
import java.util.Set;

public record Hero(
    Long id,
    String name,
    String heroName,
    LocalDate birthDate,
    Double height,
    Double weight,
    Set<Superpower> superpowers) {}
