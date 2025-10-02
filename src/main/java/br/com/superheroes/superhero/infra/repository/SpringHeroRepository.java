package br.com.superheroes.superhero.infra.repository;

import br.com.superheroes.superhero.infra.schema.HeroSchema;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringHeroRepository extends JpaRepository<HeroSchema, Long> {
    Optional<HeroSchema> findByHeroName(String heroName);
}
