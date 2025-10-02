package br.com.superheroes.superhero.superpower.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import br.com.superheroes.superhero.superpower.domain.exception.InvalidSuperpowerException;
import br.com.superheroes.superhero.superpower.domain.exception.SuperpowerNotFoundException;
import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import br.com.superheroes.superhero.superpower.domain.repository.SuperpowerRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SuperpowerDomainServiceTest {
  @InjectMocks private SuperpowerDomainService service;
  @Mock private SuperpowerRepository repository;

  @Test
  void whenCreateSuperpower_shouldReturnSaved() {
    Superpower sp = new Superpower(null, "Voo", "Capacidade de voar");
    Superpower saved = new Superpower(1L, "Voo", "Capacidade de voar");

    when(repository.save(sp)).thenReturn(saved);

    Superpower result = service.createSuperpower(sp);

    assertThat(result).isEqualTo(saved);
    verify(repository).save(sp);
  }

  @Test
  void whenFindAll_shouldReturnList() {
    Superpower sp1 = new Superpower(1L, "Voo", "Capacidade de voar");
    Superpower sp2 = new Superpower(2L, "Força", "Super força");

    when(repository.findAll()).thenReturn(List.of(sp1, sp2));

    List<Superpower> result = service.findAll();

    assertThat(result).containsExactly(sp1, sp2);
    verify(repository).findAll();
  }

  @Test
  void whenFindByIdOrThrow_shouldReturnSuperpower() {
    Superpower sp = new Superpower(1L, "Voo", "Capacidade de voar");

    when(repository.findById(1L)).thenReturn(Optional.of(sp));

    Superpower result = service.findByIdOrThrow(1L);

    assertThat(result).isEqualTo(sp);
    verify(repository).findById(1L);
  }

  @Test
  void whenFindByIdOrThrowNonExistent_shouldThrowException() {
    when(repository.findById(999L)).thenReturn(Optional.empty());

    assertThrows(SuperpowerNotFoundException.class, () -> service.findByIdOrThrow(999L));
    verify(repository).findById(999L);
  }

  @Test
  void whenExistsByIdOrThrow_shouldPassIfExists() {
    when(repository.existsById(1L)).thenReturn(true);

    service.existsByIdOrThrow(1L);

    verify(repository).existsById(1L);
  }

  @Test
  void whenExistsByIdOrThrowNonExistent_shouldThrowException() {
    when(repository.existsById(999L)).thenReturn(false);

    assertThrows(SuperpowerNotFoundException.class, () -> service.existsByIdOrThrow(999L));
    verify(repository).existsById(999L);
  }

  @Test
  void whenDeleteSuperpowerById_shouldCallRepository() {
    when(repository.existsById(1L)).thenReturn(true);

    service.deleteSuperpowerById(1L);

    verify(repository).existsById(1L);
    verify(repository).deleteById(1L);
  }

  @Test
  void whenDeleteSuperpowerByIdNonExistent_shouldThrowException() {
    when(repository.existsById(999L)).thenReturn(false);

    assertThrows(SuperpowerNotFoundException.class, () -> service.deleteSuperpowerById(999L));
    verify(repository).existsById(999L);
    verify(repository, never()).deleteById(anyLong());
  }

  @Test
  void whenUpdateSuperpower_shouldReturnUpdated() {
    Superpower sp = new Superpower(1L, "Voo", "Capacidade de voar");
    when(repository.existsById(1L)).thenReturn(true);
    when(repository.save(sp)).thenReturn(sp);

    Superpower result = service.updateSuperpower(sp);

    assertThat(result).isEqualTo(sp);
    verify(repository).existsById(1L);
    verify(repository).save(sp);
  }

  @Test
  void whenUpdateSuperpowerNonExistent_shouldThrowException() {
    Superpower sp = new Superpower(999L, "Voo", "Capacidade de voar");
    when(repository.existsById(999L)).thenReturn(false);

    assertThrows(SuperpowerNotFoundException.class, () -> service.updateSuperpower(sp));
    verify(repository).existsById(999L);
    verify(repository, never()).save(any());
  }

  @Test
  void whenFindAllByIdsOrThrow_shouldReturnSet() {
    Superpower sp1 = new Superpower(1L, "Voo", "Capacidade de voar");
    Superpower sp2 = new Superpower(2L, "Força", "Super força");
    Set<Long> ids = Set.of(1L, 2L);

    when(repository.findAllById(ids)).thenReturn(List.of(sp1, sp2));

    Set<Superpower> result = service.findAllByIdsOrThrow(ids);

    assertThat(result).containsExactlyInAnyOrder(sp1, sp2);
    verify(repository).findAllById(ids);
  }

  @Test
  void whenFindAllByIdsOrThrowMissing_shouldThrowException() {
    Superpower sp1 = new Superpower(1L, "Voo", "Capacidade de voar");
    Set<Long> ids = Set.of(1L, 2L);

    when(repository.findAllById(ids)).thenReturn(List.of(sp1));

    assertThrows(InvalidSuperpowerException.class, () -> service.findAllByIdsOrThrow(ids));
    verify(repository).findAllById(ids);
  }
}
