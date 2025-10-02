package br.com.superheroes.superhero.hero.application;

import br.com.superheroes.superhero.hero.application.dto.CreateHeroDto;
import br.com.superheroes.superhero.hero.application.dto.FindHeroDto;
import br.com.superheroes.superhero.hero.application.dto.HeroOutputDto;
import br.com.superheroes.superhero.hero.application.dto.UpdateHeroDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/heroes")
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

  @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
  public ResponseEntity<HeroOutputDto> updateHeroById(
      @Valid @ModelAttribute FindHeroDto find, @Valid @RequestBody UpdateHeroDto body) {
    HeroOutputDto updatedHero = service.updateHeroById(find.getId(), body);
    return ResponseEntity.ok(updatedHero);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteHeroById(@Valid @ModelAttribute FindHeroDto find) {
    service.deleteHeroById(find.getId());
    return ResponseEntity.noContent().build();
  }
}
