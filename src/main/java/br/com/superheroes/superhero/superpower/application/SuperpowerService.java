package br.com.superheroes.superhero.superpower.application;

import br.com.superheroes.superhero.superpower.application.dto.CreateSuperpowerDto;
import br.com.superheroes.superhero.superpower.application.dto.SuperpowerOutputDto;
import br.com.superheroes.superhero.superpower.application.dto.UpdateSuperpowerDto;
import br.com.superheroes.superhero.superpower.application.mapper.SuperpowerMapper;
import br.com.superheroes.superhero.superpower.domain.SuperpowerDomainService;
import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuperpowerService {
  private final SuperpowerDomainService service;
  private final SuperpowerMapper mapper;

  public SuperpowerOutputDto createSuperpower(CreateSuperpowerDto body) {
    Superpower superpower = mapper.toDomain(body);
    Superpower saved = service.createSuperpower(superpower);
    return mapper.toOutput(saved);
  }

  public List<SuperpowerOutputDto> getSuperpowers() {
    List<Superpower> superpowers = service.findAll();
    return mapper.toOutput(superpowers);
  }

  public SuperpowerOutputDto getSuperpowerById(Long id) {
    Superpower superpower = service.findByIdOrThrow(id);
    return mapper.toOutput(superpower);
  }

  public SuperpowerOutputDto updateSuperpowerById(Long id, UpdateSuperpowerDto body) {
    Superpower superpower = mapper.toDomain(id, body);
    Superpower updated = service.updateSuperpower(superpower);
    return mapper.toOutput(updated);
  }

  public void deleteSuperpowerById(Long id) {
    service.deleteSuperpowerById(id);
  }
}
