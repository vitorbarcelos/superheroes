package br.com.superheroes.superhero.hero.infra.schema;

import br.com.superheroes.superhero.superpower.infra.schema.SuperpowerSchema;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Herois")
public class HeroSchema {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "Nome", nullable = false, length = 120)
  private String name;

  @Column(name = "NomeHeroi", nullable = false, length = 120, unique = true)
  private String heroName;

  @Column(name = "DataNascimento")
  private LocalDate birthDate;

  @Column(name = "Altura", nullable = false)
  private Double height;

  @Column(name = "Peso", nullable = false)
  private Double weight;

  @ManyToMany
  @JoinTable(
      name = "HeroisSuperpoderes",
      joinColumns = @JoinColumn(name = "HeroiId"),
      inverseJoinColumns = @JoinColumn(name = "SuperpoderId"))
  private Set<SuperpowerSchema> superpowers = new HashSet<>();
}
