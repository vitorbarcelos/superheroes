package br.com.superheroes.superhero.infra.repository;

import br.com.superheroes.superhero.domain.model.Superpower;
import br.com.superheroes.superhero.domain.repository.SuperpowerRepository;
import br.com.superheroes.superhero.infra.mapper.SuperpowerSchemaMapper;
import br.com.superheroes.superhero.infra.schema.SuperpowerSchema;
import java.util.List;
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

  public List<Superpower> findAllById(Set<Long> ids) {
    List<SuperpowerSchema> schemas = springRepository.findAllById(ids);
    return mapper.toDomain(schemas);
  }
}
