package br.com.superheroes.superhero.superpower.application.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.superheroes.superhero.superpower.application.dto.SuperpowerDataDto;
import br.com.superheroes.superhero.superpower.application.dto.SuperpowerOutputDto;
import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SuperpowerMapperTest {
  private SuperpowerMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new SuperpowerMapper() {};
  }

  @Test
  void toOutput_shouldMapDomainToDto() {
    Superpower sp = new Superpower(1L, "Inteligência", "Alta inteligência");

    SuperpowerOutputDto dto = mapper.toOutput(sp);

    assertThat(dto.getId()).isEqualTo(1L);
    assertThat(dto.getName()).isEqualTo("Inteligência");
    assertThat(dto.getDescription()).isEqualTo("Alta inteligência");
  }

  @Test
  void toOutput_shouldMapListOfSuperpowers() {
    Superpower sp1 = new Superpower(1L, "Inteligência", "Alta inteligência");
    Superpower sp2 = new Superpower(2L, "Força", "Super força");

    List<SuperpowerOutputDto> dtos = mapper.toOutput(List.of(sp1, sp2));

    assertThat(dtos).hasSize(2);
    assertThat(dtos)
        .extracting(SuperpowerOutputDto::getName)
        .containsExactlyInAnyOrder("Inteligência", "Força");
  }

  @Test
  void toDomain_shouldMapDtoToDomain() {
    SuperpowerDataDto dto =
        new SuperpowerDataDto() {
          @Override
          public String getName() {
            return "Força";
          }

          @Override
          public String getDescription() {
            return "Super força";
          }
        };

    Superpower sp = mapper.toDomain(dto);

    assertThat(sp.id()).isNull();
    assertThat(sp.name()).isEqualTo("Força");
    assertThat(sp.description()).isEqualTo("Super força");
    assertThat(sp.heroes()).isEmpty();
  }

  @Test
  void toDomain_withId_shouldMapDtoToDomain() {
    SuperpowerDataDto dto =
        new SuperpowerDataDto() {
          @Override
          public String getName() {
            return "Velocidade";
          }

          @Override
          public String getDescription() {
            return "Super velocidade";
          }
        };

    Superpower sp = mapper.toDomain(3L, dto);

    assertThat(sp.id()).isEqualTo(3L);
    assertThat(sp.name()).isEqualTo("Velocidade");
    assertThat(sp.description()).isEqualTo("Super velocidade");
    assertThat(sp.heroes()).isEmpty();
  }
}
