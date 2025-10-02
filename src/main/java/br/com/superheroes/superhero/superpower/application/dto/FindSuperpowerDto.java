package br.com.superheroes.superhero.superpower.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindSuperpowerDto {
  @NotNull(message = "O ID do superpoder é obrigatório")
  private Long id;
}
