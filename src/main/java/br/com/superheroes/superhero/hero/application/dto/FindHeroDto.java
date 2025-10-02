package br.com.superheroes.superhero.hero.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindHeroDto {
  @NotNull(message = "O ID do herói é obrigatório")
  private Long id;
}
