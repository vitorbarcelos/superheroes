package br.com.superheroes.superhero.domain.repository;

import br.com.superheroes.superhero.domain.model.Superpower;
import java.util.List;
import java.util.Set;

public interface SuperpowerRepository {
  Superpower save(Superpower superpower);

  List<Superpower> findAllById(Set<Long> ids);
}
