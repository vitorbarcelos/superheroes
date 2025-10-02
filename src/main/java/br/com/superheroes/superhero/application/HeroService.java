package br.com.superheroes.superhero.application;

import br.com.superheroes.superhero.application.dto.CreateHeroDto;
import br.com.superheroes.superhero.application.dto.HeroOutputDto;
import br.com.superheroes.superhero.application.mapper.HeroMapper;
import br.com.superheroes.superhero.domain.HeroDomainService;
import br.com.superheroes.superhero.domain.SuperpowerDomainService;
import br.com.superheroes.superhero.domain.model.Hero;
import br.com.superheroes.superhero.domain.model.Superpower;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeroService {
  private final SuperpowerDomainService superpowerDomainService;
  private final HeroDomainService heroDomainService;
  private final HeroMapper mapper;

  public HeroOutputDto createHero(CreateHeroDto body) {
    Set<Superpower> superpowers =
        superpowerDomainService.findAllByIdsOrThrow(body.getSuperpowers());
    Hero hero = mapper.toDomain(body, superpowers);
    Hero createdHero = heroDomainService.createHero(hero);
    return mapper.toOutput(createdHero);
  }

  public List<HeroOutputDto> getHeroes() {
    List<Hero> heroes = heroDomainService.findAll();
    return mapper.toOutput(heroes);
  }

  public HeroOutputDto getHeroById(Long id) {
    Hero hero = heroDomainService.findByIdOrThrow(id);
    return mapper.toOutput(hero);
  }
}
