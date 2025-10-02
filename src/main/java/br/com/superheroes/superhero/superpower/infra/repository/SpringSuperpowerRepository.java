package br.com.superheroes.superhero.superpower.infra.repository;

import br.com.superheroes.superhero.superpower.infra.schema.SuperpowerSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringSuperpowerRepository extends JpaRepository<SuperpowerSchema, Long> {}
