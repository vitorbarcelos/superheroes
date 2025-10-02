package br.com.superheroes.superhero.superpower.domain.exception;

import br.com.superheroes.shared.exception.SuperHeroException;

public class SuperpowerNotFoundException extends SuperHeroException {
  public SuperpowerNotFoundException(Long id) {
    super("Superpoder com id '" + id + "' não encontrado");
  }

  public SuperpowerNotFoundException(Iterable<Long> ids) {
    super("Superpoder(es) não encontrado(s) para ID(s): " + ids);
  }
}
