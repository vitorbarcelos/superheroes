package br.com.superheroes.superhero.hero.application.dto;

import br.com.superheroes.superhero.superpower.application.dto.SuperpowerOutputDto;
import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HeroOutputDto {
  private Long id;
  private String name;
  private Double height;
  private Double weight;
  private String heroName;
  private LocalDate birthDate;
  private Set<SuperpowerOutputDto> superpowers;
}
