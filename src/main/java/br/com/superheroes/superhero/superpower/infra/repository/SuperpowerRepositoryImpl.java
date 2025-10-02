package br.com.superheroes.superhero.superpower.infra.repository;

import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import br.com.superheroes.superhero.superpower.domain.repository.SuperpowerRepository;
import br.com.superheroes.superhero.superpower.infra.mapper.SuperpowerSchemaMapper;
import br.com.superheroes.superhero.superpower.infra.schema.SuperpowerSchema;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SuperpowerRepositoryImpl implements SuperpowerRepository {
  private final SpringSuperpowerRepository springRepository;
  private final SuperpowerSchemaMapper mapper;

  @Override
  public Superpower save(Superpower superpower) {
    SuperpowerSchema schema = mapper.toSchema(superpower);
    SuperpowerSchema saved = springRepository.save(schema);
    return mapper.toDomain(saved);
  }

  @Override
  public List<Superpower> findAll() {
    List<SuperpowerSchema> schemas = springRepository.findAll();
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
  public Optional<Superpower> findById(Long id) {
    return springRepository.findById(id).map(mapper::toDomain);
  }

  public List<Superpower> findAllById(Set<Long> ids) {
    List<SuperpowerSchema> schemas = springRepository.findAllById(ids);
    return mapper.toDomain(schemas);
  }
}
