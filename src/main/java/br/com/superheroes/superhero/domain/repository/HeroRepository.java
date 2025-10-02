package br.com.superheroes.superhero.domain.repository;

import br.com.superheroes.superhero.domain.model.Hero;
import java.util.List;
import java.util.Optional;

public interface HeroRepository {
  Hero save(Hero hero);

  List<Hero> findAll();

  Optional<Hero> findById(Long id);

  Optional<Hero> findByHeroName(String heroName);
}
