package br.com.superheroes.superhero.superpower.application.mapper;

import br.com.superheroes.superhero.superpower.application.dto.SuperpowerDataDto;
import br.com.superheroes.superhero.superpower.application.dto.SuperpowerOutputDto;
import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuperpowerMapper {
  default List<SuperpowerOutputDto> toOutput(List<Superpower> superpowers) {
    return superpowers.stream().map(this::toOutput).collect(Collectors.toList());
  }

  default SuperpowerOutputDto toOutput(Superpower superpower) {
    Long id = superpower.id();
    String name = superpower.name();
    String description = superpower.description();
    return new SuperpowerOutputDto(id, name, description);
  }

  default Superpower toDomain(SuperpowerDataDto dto) {
    return toDomain(null, dto);
  }

  default Superpower toDomain(Long id, SuperpowerDataDto dto) {
    String name = dto.getName();
    String description = dto.getDescription();
    return new Superpower(id, name, description);
  }
}
