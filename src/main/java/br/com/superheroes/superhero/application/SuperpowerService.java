package br.com.superheroes.superhero.application;

import br.com.superheroes.superhero.application.dto.CreateSuperpowerDto;
import br.com.superheroes.superhero.application.dto.SuperpowerOutputDto;
import br.com.superheroes.superhero.application.mapper.SuperpowerMapper;
import br.com.superheroes.superhero.domain.SuperpowerDomainService;
import br.com.superheroes.superhero.domain.model.Superpower;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuperpowerService {
  private final SuperpowerDomainService service;
  private final SuperpowerMapper mapper;

  public SuperpowerOutputDto createSuperpower(CreateSuperpowerDto body) {
    Superpower superpower = mapper.toDomain(body);
    Superpower createdSuperpower = service.createSuperpower(superpower);
    return mapper.toOutput(createdSuperpower);
  }
}
