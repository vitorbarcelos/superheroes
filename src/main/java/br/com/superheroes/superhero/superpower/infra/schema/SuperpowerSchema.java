package br.com.superheroes.superhero.superpower.infra.schema;

import br.com.superheroes.superhero.hero.infra.schema.HeroSchema;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "Superpoderes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuperpowerSchema {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "Superpoder", nullable = false, length = 50)
  private String name;

  @Column(name = "Descricao", length = 250)
  private String description;

  @ManyToMany(mappedBy = "superpowers")
  private Set<HeroSchema> heroes = new HashSet<>();
}
