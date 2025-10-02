package br.com.superheroes.superhero.domain.exception;

public class SuperpowerNotFoundException extends SuperHeroException {
  public SuperpowerNotFoundException(Iterable<Long> ids) {
    super("Superpoder(es) não encontrado(s) para ID(s): " + ids);
  }
}
