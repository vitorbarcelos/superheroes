package br.com.superheroes.superhero.superpower.domain;

import br.com.superheroes.superhero.superpower.domain.exception.InvalidSuperpowerException;
import br.com.superheroes.superhero.superpower.domain.exception.SuperpowerNotFoundException;
import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import br.com.superheroes.superhero.superpower.domain.repository.SuperpowerRepository;
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

  public List<Superpower> findAll() {
    return repository.findAll();
  }

  public Superpower findByIdOrThrow(Long id) {
    return repository.findById(id).orElseThrow(() -> new SuperpowerNotFoundException(id));
  }

  public void existsByIdOrThrow(Long id) {
    boolean exists = repository.existsById(id);
    if (!exists) throw new SuperpowerNotFoundException(id);
  }

  public void deleteSuperpowerById(Long id) {
    existsByIdOrThrow(id);
    repository.deleteById(id);
  }

  public Superpower updateSuperpower(Superpower superpower) {
    existsByIdOrThrow(superpower.id());
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
