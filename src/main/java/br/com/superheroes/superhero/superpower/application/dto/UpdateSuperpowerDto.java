package br.com.superheroes.superhero.superpower.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSuperpowerDto extends SuperpowerDataDto {
  @NotBlank(message = "O nome do superpoder é obrigatório")
  @Size(max = 50, message = "O nome do superpoder deve ter no máximo 50 caracteres")
  private String name;

  @Size(max = 250, message = "A descrição do suporpoder deve ter no máximo 250 caracteres")
  private String description;
}
