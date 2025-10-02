package br.com.superheroes.superhero.application;

import br.com.superheroes.superhero.application.dto.CreateSuperpowerDto;
import br.com.superheroes.superhero.application.dto.SuperpowerOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/superpowers")
@RequiredArgsConstructor
@Tag(name = "Superpoderes", description = "Operações para gerenciamento de superpoderes")
public class SuperpowerController {
    private final SuperpowerService service;

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(
            summary = "Criar novo superpoder",
            description = "Cadastra um novo superpoder no sistema"
    )
    public ResponseEntity<SuperpowerOutputDto> createSuperpower(
            @Valid @RequestBody CreateSuperpowerDto body) {
        SuperpowerOutputDto output = service.createSuperpower(body);
        return new ResponseEntity<>(output, HttpStatus.CREATED);
    }
}