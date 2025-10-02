package br.com.superheroes.superhero.application;

import br.com.superheroes.superhero.application.dto.CreateHeroDto;
import br.com.superheroes.superhero.application.dto.FindHeroDto;
import br.com.superheroes.superhero.application.dto.HeroOutputDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/heroes")
@Tag(name = "Heróis", description = "Operações para gerenciamento de super-heróis")
public class HeroController {
  private final HeroService service;

  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<HeroOutputDto> createHero(@Valid @RequestBody CreateHeroDto body) {
    HeroOutputDto output = service.createHero(body);
    return new ResponseEntity<>(output, HttpStatus.CREATED);
  }

  @GetMapping(produces = "application/json")
  public ResponseEntity<List<HeroOutputDto>> getHeroes() {
    List<HeroOutputDto> heroes = service.getHeroes();
    return ResponseEntity.ok(heroes);
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<HeroOutputDto> getHeroById(@Valid @ModelAttribute FindHeroDto find) {
    HeroOutputDto hero = service.getHeroById(find.getId());
    return ResponseEntity.ok(hero);
  }

  //  @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
  //  public ResponseEntity<HeroOutputDto> updateHero(@Valid @ModelAttribute FindHeroDto find,
  // @Valid @RequestBody UpdateHeroDto body) {
  //    HeroOutputDto updatedHero = service.updateHero(find.getId(), body);
  //    return ResponseEntity.ok(updatedHero);
  //  }
  //
  //  @DeleteMapping("/{id}")
  //  public ResponseEntity<Void> deleteHero(@Valid @ModelAttribute FindHeroDto find) {
  //    service.deleteHero(find.getId());
  //    return ResponseEntity.noContent().build();
  //  }
}
