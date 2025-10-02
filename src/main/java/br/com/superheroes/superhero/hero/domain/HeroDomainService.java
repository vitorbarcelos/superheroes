package br.com.superheroes.superhero.hero.domain;

import br.com.superheroes.superhero.hero.domain.exception.HeroNameConflictException;
import br.com.superheroes.superhero.hero.domain.exception.HeroNotFoundException;
import br.com.superheroes.superhero.hero.domain.model.Hero;
import br.com.superheroes.superhero.hero.domain.repository.HeroRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeroDomainService {
  private final HeroRepository repository;

  public List<Hero> findAll() {
    return repository.findAll();
  }

  public Hero findByIdOrThrow(Long id) {
    return repository.findById(id).orElseThrow(() -> new HeroNotFoundException(id));
  }

  public void existsByIdOrThrow(Long id) {
    boolean exists = repository.existsById(id);
    if (!exists) throw new HeroNotFoundException(id);
  }

  public void deleteHeroById(Long id) {
    existsByIdOrThrow(id);
    repository.deleteById(id);
  }

  public Hero createHero(Hero hero) {
    String heroName = hero.heroName();
    repository
        .findByHeroName(heroName)
        .ifPresent(
            existing -> {
              throw new HeroNameConflictException(heroName);
            });

    return repository.save(hero);
  }

  public Hero updateHero(Hero hero) {
    String heroName = hero.heroName();
    Long id = hero.id();

    existsByIdOrThrow(id);

    repository
        .findByHeroNameAndIdNot(heroName, id)
        .ifPresent(
            conflict -> {
              throw new HeroNameConflictException(heroName);
            });

    return repository.save(hero);
  }
}
