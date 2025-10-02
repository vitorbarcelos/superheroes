package br.com.superheroes.superhero.infra.repository;

import br.com.superheroes.superhero.domain.model.Hero;
import br.com.superheroes.superhero.domain.repository.HeroRepository;
import br.com.superheroes.superhero.infra.mapper.HeroSchemaMapper;
import br.com.superheroes.superhero.infra.schema.HeroSchema;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HeroRepositoryImpl implements HeroRepository {
  private final SpringHeroRepository springHeroRepository;
  private final HeroSchemaMapper mapper;

  @Override
  public Hero save(Hero hero) {
    HeroSchema schema = mapper.toSchema(hero);
    HeroSchema saved = springHeroRepository.save(schema);
    return mapper.toDomain(saved);
  }

  @Override
  public List<Hero> findAll() {
    List<HeroSchema> schemas = springHeroRepository.findAll();
    return mapper.toDomain(schemas);
  }

  @Override
  public Optional<Hero> findById(Long id) {
    return springHeroRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public Optional<Hero> findByHeroName(String heroName) {
    return springHeroRepository.findByHeroName(heroName).map(mapper::toDomain);
  }
}
