package br.com.superheroes.superhero.superpower.application;

import br.com.superheroes.superhero.superpower.application.dto.CreateSuperpowerDto;
import br.com.superheroes.superhero.superpower.application.dto.FindSuperpowerDto;
import br.com.superheroes.superhero.superpower.application.dto.SuperpowerOutputDto;
import br.com.superheroes.superhero.superpower.application.dto.UpdateSuperpowerDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/superpowers")
@RequiredArgsConstructor
public class SuperpowerController {
  private final SuperpowerService service;

  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<SuperpowerOutputDto> createSuperpower(
      @Valid @RequestBody CreateSuperpowerDto body) {
    SuperpowerOutputDto output = service.createSuperpower(body);
    return new ResponseEntity<>(output, HttpStatus.CREATED);
  }

  @GetMapping(produces = "application/json")
  public ResponseEntity<List<SuperpowerOutputDto>> getSuperpowers() {
    List<SuperpowerOutputDto> output = service.getSuperpowers();
    return ResponseEntity.ok(output);
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<SuperpowerOutputDto> getSuperpowerById(
      @Valid @ModelAttribute FindSuperpowerDto find) {
    SuperpowerOutputDto output = service.getSuperpowerById(find.getId());
    return ResponseEntity.ok(output);
  }

  @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
  public ResponseEntity<SuperpowerOutputDto> updateSuperpowerById(
      @Valid @ModelAttribute FindSuperpowerDto find, @Valid @RequestBody UpdateSuperpowerDto body) {
    SuperpowerOutputDto output = service.updateSuperpowerById(find.getId(), body);
    return ResponseEntity.ok(output);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSuperpowerById(@Valid @ModelAttribute FindSuperpowerDto find) {
    service.deleteSuperpowerById(find.getId());
    return ResponseEntity.noContent().build();
  }
}
