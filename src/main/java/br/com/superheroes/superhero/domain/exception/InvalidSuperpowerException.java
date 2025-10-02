package br.com.superheroes.superhero.domain.exception;

public class InvalidSuperpowerException extends SuperHeroException {
  public InvalidSuperpowerException(Iterable<Long> ids) {
    super("Superpoder(es) não encontrado(s) para ID(s): " + ids);
  }
}
