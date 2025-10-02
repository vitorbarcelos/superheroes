package br.com.superheroes.superhero.superpower.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import br.com.superheroes.superhero.superpower.infra.mapper.SuperpowerSchemaMapper;
import br.com.superheroes.superhero.superpower.infra.schema.SuperpowerSchema;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SuperpowerRepositoryImplTest {
  @Mock private SpringSuperpowerRepository springRepository;
  @InjectMocks private SuperpowerRepositoryImpl repository;
  @Mock private SuperpowerSchemaMapper mapper;

  @Test
  void whenSave_shouldMapAndReturn() {
    Superpower domain = new Superpower(null, "Voo", "Capacidade de voar", Set.of());
    SuperpowerSchema schema = new SuperpowerSchema(null, "Voo", "Capacidade de voar", Set.of());
    SuperpowerSchema savedSchema = new SuperpowerSchema(1L, "Voo", "Capacidade de voar", Set.of());
    Superpower savedDomain = new Superpower(1L, "Voo", "Capacidade de voar", Set.of());

    when(mapper.toSchema(domain)).thenReturn(schema);
    when(springRepository.save(schema)).thenReturn(savedSchema);
    when(mapper.toDomain(savedSchema)).thenReturn(savedDomain);

    Superpower result = repository.save(domain);

    assertThat(result).isEqualTo(savedDomain);
    verify(mapper).toSchema(domain);
    verify(springRepository).save(schema);
    verify(mapper).toDomain(savedSchema);
  }

  @Test
  void whenFindAll_shouldReturnMappedList() {
    SuperpowerSchema schema1 = new SuperpowerSchema(1L, "Voo", "Capacidade de voar", Set.of());
    SuperpowerSchema schema2 = new SuperpowerSchema(2L, "Força", "Super força", Set.of());
    List<SuperpowerSchema> schemas = List.of(schema1, schema2);

    Superpower sp1 = new Superpower(1L, "Voo", "Capacidade de voar", Set.of());
    Superpower sp2 = new Superpower(2L, "Força", "Super força", Set.of());

    when(springRepository.findAll()).thenReturn(schemas);
    when(mapper.toDomain(schemas)).thenReturn(List.of(sp1, sp2));

    List<Superpower> result = repository.findAll();

    assertThat(result).containsExactly(sp1, sp2);
    verify(springRepository).findAll();
    verify(mapper).toDomain(schemas);
  }

  @Test
  void whenDeleteById_shouldCallSpringRepository() {
    repository.deleteById(1L);

    verify(springRepository).deleteById(1L);
  }

  @Test
  void whenExistsById_shouldReturnBoolean() {
    when(springRepository.existsById(1L)).thenReturn(true);

    boolean exists = repository.existsById(1L);

    assertThat(exists).isTrue();
    verify(springRepository).existsById(1L);
  }

  @Test
  void whenFindById_shouldReturnMappedOptional() {
    SuperpowerSchema schema = new SuperpowerSchema(1L, "Voo", "Capacidade de voar", Set.of());
    Superpower domain = new Superpower(1L, "Voo", "Capacidade de voar", Set.of());

    when(springRepository.findById(1L)).thenReturn(Optional.of(schema));
    when(mapper.toDomain(schema)).thenReturn(domain);

    Optional<Superpower> result = repository.findById(1L);

    assertThat(result).contains(domain);
    verify(springRepository).findById(1L);
    verify(mapper).toDomain(schema);
  }

  @Test
  void whenFindAllById_shouldReturnMappedList() {
    Set<Long> ids = Set.of(1L, 2L);
    SuperpowerSchema schema1 = new SuperpowerSchema(1L, "Voo", "Capacidade de voar", Set.of());
    SuperpowerSchema schema2 = new SuperpowerSchema(2L, "Força", "Super força", Set.of());
    List<SuperpowerSchema> schemas = List.of(schema1, schema2);

    Superpower sp1 = new Superpower(1L, "Voo", "Capacidade de voar", Set.of());
    Superpower sp2 = new Superpower(2L, "Força", "Super força", Set.of());

    when(springRepository.findAllById(ids)).thenReturn(schemas);
    when(mapper.toDomain(schemas)).thenReturn(List.of(sp1, sp2));

    List<Superpower> result = repository.findAllById(ids);

    assertThat(result).containsExactly(sp1, sp2);
    verify(springRepository).findAllById(ids);
    verify(mapper).toDomain(schemas);
  }
}
