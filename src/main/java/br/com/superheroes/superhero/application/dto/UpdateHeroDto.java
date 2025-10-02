package br.com.superheroes.superhero.application.dto;

import br.com.superheroes.superhero.application.validation.ValidDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class UpdateHeroDto {
  @NotBlank(message = "O nome é obrigatório")
  @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
  private String name;

  @NotBlank(message = "O nome do herói é obrigatório")
  @Size(max = 120, message = "O nome do herói deve ter no máximo 120 caracteres")
  private String heroName;

  @NotNull(message = "A altura é obrigatória")
  @Positive(message = "A altura deve ser maior que zero")
  private Double height;

  @NotNull(message = "O peso é obrigatório")
  @Positive(message = "O peso deve ser maior que zero")
  private Double weight;

  @ValidDate(pattern = "yyyy-MM-dd", message = "A data de nascimento está inválida")
  @NotNull(message = "A data de nascimento é obrigatória")
  private LocalDate birthDate;

  @NotNull(message = "A lista de superpoderes é obrigatória")
  @Size(min = 1, message = "Deve haver pelo menos um superpoder")
  private Set<@NotNull(message = "O ID do superpoder não pode ser nulo") Long> superpowers;
}
