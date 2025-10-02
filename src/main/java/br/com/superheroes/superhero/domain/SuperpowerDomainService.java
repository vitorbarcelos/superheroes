package br.com.superheroes.superhero.domain;

import br.com.superheroes.superhero.domain.exception.InvalidSuperpowerException;
import br.com.superheroes.superhero.domain.model.Superpower;
import br.com.superheroes.superhero.domain.repository.SuperpowerRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuperpowerDomainService {
  private final SuperpowerRepository repository;

  public Superpower createSuperpower(Superpower superpower) {
    return repository.save(superpower);
  }

  public Set<Superpower> findAllByIdsOrThrow(Set<Long> ids) {
    List<Superpower> superpowers = repository.findAllById(ids);

    Set<Long> foundIds = superpowers.stream().map(Superpower::id).collect(Collectors.toSet());

    Set<Long> missingIds =
        ids.stream().filter(id -> !foundIds.contains(id)).collect(Collectors.toSet());

    if (!missingIds.isEmpty()) {
      throw new InvalidSuperpowerException(missingIds);
    }

    return new HashSet<>(superpowers);
  }
}
