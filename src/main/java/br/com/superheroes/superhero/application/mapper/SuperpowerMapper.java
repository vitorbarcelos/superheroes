package br.com.superheroes.superhero.application.mapper;

import br.com.superheroes.superhero.application.dto.CreateSuperpowerDto;
import br.com.superheroes.superhero.application.dto.SuperpowerOutputDto;
import br.com.superheroes.superhero.domain.model.Superpower;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuperpowerMapper {
  default SuperpowerOutputDto toOutput(Superpower superpower) {
    Long id = superpower.id();
    String name = superpower.name();
    String description = superpower.description();
    return new SuperpowerOutputDto(id, name, description);
  }

  default Superpower toDomain(CreateSuperpowerDto dto) {
    String name = dto.getName();
    String description = dto.getDescription();
    return new Superpower(null, name, description);
  }
}
