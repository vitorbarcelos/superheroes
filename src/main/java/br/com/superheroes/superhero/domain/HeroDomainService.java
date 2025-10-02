package br.com.superheroes.superhero.domain;

import br.com.superheroes.superhero.domain.exception.HeroNameConflictException;
import br.com.superheroes.superhero.domain.exception.HeroNotFoundException;
import br.com.superheroes.superhero.domain.model.Hero;
import br.com.superheroes.superhero.domain.repository.HeroRepository;
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
}
