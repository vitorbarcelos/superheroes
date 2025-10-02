package br.com.superheroes.superhero.superpower.domain.exception;

import br.com.superheroes.shared.exception.SuperHeroException;

public class InvalidSuperpowerException extends SuperHeroException {
  public InvalidSuperpowerException(Iterable<Long> ids) {
    super("Superpoder(es) não encontrado(s) para ID(s): " + ids);
  }
}
