package br.com.superheroes.superhero.hero.infra.repository;

import br.com.superheroes.superhero.hero.domain.model.Hero;
import br.com.superheroes.superhero.hero.domain.repository.HeroRepository;
import br.com.superheroes.superhero.hero.infra.mapper.HeroSchemaMapper;
import br.com.superheroes.superhero.hero.infra.schema.HeroSchema;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HeroRepositoryImpl implements HeroRepository {
  private final SpringHeroRepository springRepository;
  private final HeroSchemaMapper mapper;

  @Override
  public Hero save(Hero hero) {
    HeroSchema schema = mapper.toSchema(hero);
    HeroSchema saved = springRepository.save(schema);
    return mapper.toDomain(saved);
  }

  @Override
  public List<Hero> findAll() {
    List<HeroSchema> schemas = springRepository.findAll();
    return mapper.toDomain(schemas);
  }

  @Override
  public void deleteById(Long id) {
    springRepository.deleteById(id);
  }

  @Override
  public boolean existsById(Long id) {
    return springRepository.existsById(id);
  }

  @Override
  public Optional<Hero> findById(Long id) {
    return springRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public Optional<Hero> findByHeroName(String heroName) {
    return springRepository.findByHeroName(heroName).map(mapper::toDomain);
  }

  @Override
  public Optional<Hero> findByHeroNameAndIdNot(String heroName, Long id) {
    return springRepository.findByHeroNameAndIdNot(heroName, id).map(mapper::toDomain);
  }
}
