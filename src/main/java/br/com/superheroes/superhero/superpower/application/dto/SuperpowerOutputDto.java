package br.com.superheroes.superhero.superpower.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SuperpowerOutputDto {
  private Long id;
  private String name;
  private String description;
}
