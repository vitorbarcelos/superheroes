package br.com.superheroes.superhero.superpower.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import br.com.superheroes.superhero.superpower.application.dto.CreateSuperpowerDto;
import br.com.superheroes.superhero.superpower.application.dto.SuperpowerOutputDto;
import br.com.superheroes.superhero.superpower.application.dto.UpdateSuperpowerDto;
import br.com.superheroes.superhero.superpower.application.mapper.SuperpowerMapper;
import br.com.superheroes.superhero.superpower.domain.SuperpowerDomainService;
import br.com.superheroes.superhero.superpower.domain.exception.SuperpowerNotFoundException;
import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SuperpowerServiceTest {
  @Mock private SuperpowerDomainService domainService;
  @InjectMocks private SuperpowerService service;
  @Mock private SuperpowerMapper mapper;

  @Test
  void whenCreateSuperpower_shouldReturnCreatedSuperpower() {
    CreateSuperpowerDto dto = new CreateSuperpowerDto("Voo", "Capacidade de voar");
    Superpower domain = new Superpower(null, "Voo", "Capacidade de voar");
    Superpower savedDomain = new Superpower(1L, "Voo", "Capacidade de voar");
    SuperpowerOutputDto output = new SuperpowerOutputDto(1L, "Voo", "Capacidade de voar");

    when(mapper.toDomain(dto)).thenReturn(domain);
    when(domainService.createSuperpower(domain)).thenReturn(savedDomain);
    when(mapper.toOutput(savedDomain)).thenReturn(output);

    SuperpowerOutputDto result = service.createSuperpower(dto);

    assertThat(result).isEqualTo(output);
    verify(mapper).toDomain(dto);
    verify(domainService).createSuperpower(domain);
    verify(mapper).toOutput(savedDomain);
  }

  @Test
  void whenGetSuperpowers_shouldReturnList() {
    Superpower sp1 = new Superpower(1L, "Voo", "Capacidade de voar");
    Superpower sp2 = new Superpower(2L, "Força", "Super força");
    SuperpowerOutputDto dto1 = new SuperpowerOutputDto(1L, "Voo", "Capacidade de voar");
    SuperpowerOutputDto dto2 = new SuperpowerOutputDto(2L, "Força", "Super força");

    when(domainService.findAll()).thenReturn(List.of(sp1, sp2));
    when(mapper.toOutput(List.of(sp1, sp2))).thenReturn(List.of(dto1, dto2));

    List<SuperpowerOutputDto> result = service.getSuperpowers();

    assertThat(result).containsExactly(dto1, dto2);
    verify(domainService).findAll();
    verify(mapper).toOutput(List.of(sp1, sp2));
  }

  @Test
  void whenGetSuperpowerById_shouldReturnSuperpower() {
    Long id = 1L;
    Superpower domain = new Superpower(id, "Voo", "Capacidade de voar");
    SuperpowerOutputDto output = new SuperpowerOutputDto(id, "Voo", "Capacidade de voar");

    when(domainService.findByIdOrThrow(id)).thenReturn(domain);
    when(mapper.toOutput(domain)).thenReturn(output);

    SuperpowerOutputDto result = service.getSuperpowerById(id);

    assertThat(result).isEqualTo(output);
    verify(domainService).findByIdOrThrow(id);
    verify(mapper).toOutput(domain);
  }

  @Test
  void whenGetSuperpowerByIdNonExistent_shouldThrowException() {
    Long id = 999L;
    when(domainService.findByIdOrThrow(id)).thenThrow(new SuperpowerNotFoundException(id));

    assertThrows(SuperpowerNotFoundException.class, () -> service.getSuperpowerById(id));
    verify(domainService).findByIdOrThrow(id);
  }

  @Test
  void whenUpdateSuperpowerById_shouldReturnUpdated() {
    Long id = 1L;
    UpdateSuperpowerDto dto = new UpdateSuperpowerDto("Voo", "Capacidade de voar rápido");
    Superpower domain = new Superpower(id, "Voo", "Capacidade de voar rápido");
    Superpower updated = new Superpower(id, "Voo", "Capacidade de voar rápido");
    SuperpowerOutputDto output = new SuperpowerOutputDto(id, "Voo", "Capacidade de voar rápido");

    when(mapper.toDomain(id, dto)).thenReturn(domain);
    when(domainService.updateSuperpower(domain)).thenReturn(updated);
    when(mapper.toOutput(updated)).thenReturn(output);

    SuperpowerOutputDto result = service.updateSuperpowerById(id, dto);

    assertThat(result).isEqualTo(output);
    verify(mapper).toDomain(id, dto);
    verify(domainService).updateSuperpower(domain);
    verify(mapper).toOutput(updated);
  }

  @Test
  void whenUpdateSuperpowerByIdNonExistent_shouldThrowException() {
    Long id = 999L;
    UpdateSuperpowerDto dto = new UpdateSuperpowerDto("Voo", "Descrição inválida");
    Superpower domain = new Superpower(id, "Voo", "Descrição inválida");

    when(mapper.toDomain(id, dto)).thenReturn(domain);
    when(domainService.updateSuperpower(domain)).thenThrow(new SuperpowerNotFoundException(id));

    assertThrows(SuperpowerNotFoundException.class, () -> service.updateSuperpowerById(id, dto));
    verify(domainService).updateSuperpower(domain);
  }

  @Test
  void whenDeleteSuperpowerById_shouldCallDomainService() {
    Long id = 1L;

    service.deleteSuperpowerById(id);

    verify(domainService).deleteSuperpowerById(id);
  }

  @Test
  void whenDeleteSuperpowerByIdNonExistent_shouldThrowException() {
    Long id = 999L;
    doThrow(new SuperpowerNotFoundException(id)).when(domainService).deleteSuperpowerById(id);

    assertThrows(SuperpowerNotFoundException.class, () -> service.deleteSuperpowerById(id));
    verify(domainService).deleteSuperpowerById(id);
  }
}
