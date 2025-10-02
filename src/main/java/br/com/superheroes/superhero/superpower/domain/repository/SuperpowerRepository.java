package br.com.superheroes.superhero.superpower.domain.repository;

import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SuperpowerRepository {
  Superpower save(Superpower superpower);

  List<Superpower> findAll();

  void deleteById(Long id);

  boolean existsById(Long id);

  Optional<Superpower> findById(Long id);

  List<Superpower> findAllById(Set<Long> ids);
}
