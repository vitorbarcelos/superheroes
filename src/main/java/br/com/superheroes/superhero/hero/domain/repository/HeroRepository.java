package br.com.superheroes.superhero.hero.domain.repository;

import br.com.superheroes.superhero.hero.domain.model.Hero;
import java.util.List;
import java.util.Optional;

public interface HeroRepository {
  Hero save(Hero hero);

  List<Hero> findAll();

  void deleteById(Long id);

  boolean existsById(Long id);

  Optional<Hero> findById(Long id);

  Optional<Hero> findByHeroName(String heroName);

  Optional<Hero> findByHeroNameAndIdNot(String heroName, Long id);
}
