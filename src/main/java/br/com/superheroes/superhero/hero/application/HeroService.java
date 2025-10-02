package br.com.superheroes.superhero.hero.application;

import br.com.superheroes.superhero.hero.application.dto.CreateHeroDto;
import br.com.superheroes.superhero.hero.application.dto.HeroOutputDto;
import br.com.superheroes.superhero.hero.application.dto.UpdateHeroDto;
import br.com.superheroes.superhero.hero.application.mapper.HeroMapper;
import br.com.superheroes.superhero.hero.domain.HeroDomainService;
import br.com.superheroes.superhero.hero.domain.model.Hero;
import br.com.superheroes.superhero.superpower.domain.SuperpowerDomainService;
import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeroService {
  private final HeroDomainService heroDomainService;
  private final SuperpowerDomainService superpowerDomainService;
  private final HeroMapper mapper;

  public HeroOutputDto createHero(CreateHeroDto body) {
    Set<Superpower> superpowers =
        superpowerDomainService.findAllByIdsOrThrow(body.getSuperpowers());
    Hero toCreate = mapper.toDomain(body, superpowers);
    Hero created = heroDomainService.createHero(toCreate);
    return mapper.toOutput(created);
  }

  public List<HeroOutputDto> getHeroes() {
    List<Hero> heroes = heroDomainService.findAll();
    return mapper.toOutput(heroes);
  }

  public HeroOutputDto getHeroById(Long id) {
    Hero hero = heroDomainService.findByIdOrThrow(id);
    return mapper.toOutput(hero);
  }

  public HeroOutputDto updateHeroById(Long id, UpdateHeroDto body) {
    Set<Superpower> superpowers =
        superpowerDomainService.findAllByIdsOrThrow(body.getSuperpowers());
    Hero toUpdate = mapper.toDomain(id, body, superpowers);
    Hero updated = heroDomainService.updateHero(toUpdate);
    return mapper.toOutput(updated);
  }

  public void deleteHeroById(Long id) {
    heroDomainService.deleteHeroById(id);
  }
}
