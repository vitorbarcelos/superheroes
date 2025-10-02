package br.com.superheroes.superhero.hero.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.superheroes.fixtures.HeroMother;
import br.com.superheroes.fixtures.SuperPowerMother;
import br.com.superheroes.shared.exception.GlobalExceptionHandler;
import br.com.superheroes.superhero.hero.application.dto.CreateHeroDto;
import br.com.superheroes.superhero.hero.application.dto.HeroOutputDto;
import br.com.superheroes.superhero.hero.application.dto.UpdateHeroDto;
import br.com.superheroes.superhero.hero.domain.exception.HeroNameConflictException;
import br.com.superheroes.superhero.hero.domain.exception.HeroNotFoundException;
import br.com.superheroes.superhero.superpower.application.dto.SuperpowerOutputDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class HeroControllerTest {
  private final ObjectMapper objectMapper = new ObjectMapper();
  @InjectMocks private HeroController heroController;
  @Mock private HeroService heroService;
  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(heroController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    objectMapper.findAndRegisterModules();
  }

  @Test
  void whenCreateHeroWithValidData_shouldReturnCreatedHero() throws Exception {
    Set<SuperpowerOutputDto> responseSuperpowers = SuperPowerMother.getBatmanSuperpowers();
    HeroOutputDto responseDto = HeroMother.getBatmanCreatedHeroOutputDto(responseSuperpowers);
    CreateHeroDto requestDto = HeroMother.getBatmanCreateHeroDto();

    when(heroService.createHero(any(CreateHeroDto.class))).thenReturn(responseDto);

    mockMvc
        .perform(
            post("/heroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(HeroMother.BATMAN_ID))
        .andExpect(jsonPath("$.name").value(HeroMother.BATMAN_NAME))
        .andExpect(jsonPath("$.heroName").value(HeroMother.BATMAN_ALIAS))
        .andExpect(jsonPath("$.height").value(HeroMother.BATMAN_HEIGHT))
        .andExpect(jsonPath("$.weight").value(HeroMother.BATMAN_WEIGHT))
        .andExpect(jsonPath("$.birthDate").value(containsInAnyOrder(1939, 5, 1)))
        .andExpect(jsonPath("$.superpowers").isArray())
        .andExpect(jsonPath("$.superpowers.length()").value(2))
        .andExpect(
            jsonPath("$.superpowers[?(@.id == 1)].name").value(SuperPowerMother.INTELLIGENCE_NAME))
        .andExpect(
            jsonPath("$.superpowers[?(@.id == 2)].name").value(SuperPowerMother.MARTIAL_ARTS_NAME))
        .andExpect(
            jsonPath("$.superpowers[?(@.id == 1)].description")
                .value(SuperPowerMother.INTELLIGENCE_DESCRIPTION))
        .andExpect(
            jsonPath("$.superpowers[?(@.id == 2)].description")
                .value(SuperPowerMother.MARTIAL_ARTS_DESCRIPTION));
  }

  @Test
  void whenCreateHeroWithInvalidData_shouldReturnBadRequest() throws Exception {
    CreateHeroDto invalidDto = new CreateHeroDto("", "", -1.0, -1.0, null, Set.of());

    mockMvc
        .perform(
            post("/heroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(jsonPath("$.status").value(400));
  }

  @Test
  void whenCreateHeroWithDuplicateHeroName_shouldReturnConflict() throws Exception {
    CreateHeroDto requestDto = HeroMother.getBatmanCreateHeroDto();

    when(heroService.createHero(any(CreateHeroDto.class)))
        .thenThrow(new HeroNameConflictException(HeroMother.BATMAN_ALIAS));

    mockMvc
        .perform(
            post("/heroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.errors[0].message").value("Já existe um herói com o nome 'Batman'"));
  }

  @Test
  void whenCreateHero_shouldCallServiceWithCorrectParameters() throws Exception {
    Set<SuperpowerOutputDto> superpowers = SuperPowerMother.getSpidermanSuperpowers();
    HeroOutputDto responseDto = HeroMother.getSpidermanCreatedHeroOutputDto(superpowers);
    CreateHeroDto requestDto = HeroMother.getSpidermanCreateHeroDto();

    when(heroService.createHero(any(CreateHeroDto.class))).thenReturn(responseDto);

    var result =
        mockMvc
            .perform(
                post("/heroes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
            .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(201);
    verify(heroService).createHero(any(CreateHeroDto.class));
  }

  @Test
  void whenGetAllHeroes_shouldReturnListOfHeroes() throws Exception {
    HeroOutputDto hero1 =
        HeroMother.getBatmanCreatedHeroOutputDto(SuperPowerMother.getBatmanSuperpowers());
    HeroOutputDto hero2 =
        HeroMother.getSupermanCreatedHeroOutputDto(SuperPowerMother.getSupermanSuperpowers());

    List<HeroOutputDto> heroes = List.of(hero1, hero2);

    when(heroService.getHeroes()).thenReturn(heroes);

    mockMvc
        .perform(get("/heroes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].heroName").value(HeroMother.BATMAN_ALIAS))
        .andExpect(jsonPath("$[1].heroName").value(HeroMother.SUPERMAN_ALIAS))
        .andExpect(jsonPath("$[0].superpowers.length()").value(2))
        .andExpect(jsonPath("$[1].superpowers.length()").value(3));
  }

  @Test
  void whenGetAllHeroesWithEmptyList_shouldReturnEmptyList() throws Exception {
    when(heroService.getHeroes()).thenReturn(List.of());

    mockMvc
        .perform(get("/heroes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  void whenGetHeroByIdWithValidId_shouldReturnHero() throws Exception {
    Set<SuperpowerOutputDto> superpowers = SuperPowerMother.getBatmanSuperpowers();
    HeroOutputDto hero = HeroMother.getBatmanCreatedHeroOutputDto(superpowers);

    when(heroService.getHeroById(HeroMother.BATMAN_ID)).thenReturn(hero);

    mockMvc
        .perform(get("/heroes/{id}", HeroMother.BATMAN_ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(HeroMother.BATMAN_ID))
        .andExpect(jsonPath("$.name").value(HeroMother.BATMAN_NAME))
        .andExpect(jsonPath("$.heroName").value(HeroMother.BATMAN_ALIAS))
        .andExpect(jsonPath("$.height").value(HeroMother.BATMAN_HEIGHT))
        .andExpect(jsonPath("$.weight").value(HeroMother.BATMAN_WEIGHT))
        .andExpect(jsonPath("$.birthDate").value(containsInAnyOrder(1939, 5, 1)))
        .andExpect(jsonPath("$.superpowers").isArray())
        .andExpect(jsonPath("$.superpowers.length()").value(2))
        .andExpect(
            jsonPath("$.superpowers[?(@.id == 1)].name").value(SuperPowerMother.INTELLIGENCE_NAME))
        .andExpect(
            jsonPath("$.superpowers[?(@.id == 2)].name").value(SuperPowerMother.MARTIAL_ARTS_NAME));
  }

  @Test
  void whenGetHeroByIdWithNonExistentId_shouldReturnNotFound() throws Exception {
    Long nonExistentId = 999L;

    when(heroService.getHeroById(nonExistentId))
        .thenThrow(new HeroNotFoundException(nonExistentId));

    mockMvc
        .perform(get("/heroes/{id}", nonExistentId))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errors[0].message").value("Herói com id '999' não encontrado"));
  }

  @Test
  void whenUpdateHeroWithValidData_shouldReturnUpdatedHero() throws Exception {
    Set<SuperpowerOutputDto> superpowers = SuperPowerMother.getBatmanSuperpowers();
    HeroOutputDto updatedHeroDto = HeroMother.getBatmanUpdatedHeroOutputDto(superpowers);
    UpdateHeroDto updateHeroDto = HeroMother.getBatmanUpdateHeroDto();

    when(heroService.updateHeroById(eq(HeroMother.BATMAN_ID), any(UpdateHeroDto.class)))
        .thenReturn(updatedHeroDto);

    mockMvc
        .perform(
            put("/heroes/{id}", HeroMother.BATMAN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateHeroDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(HeroMother.BATMAN_ID))
        .andExpect(jsonPath("$.weight").value(96.0))
        .andExpect(jsonPath("$.heroName").value(HeroMother.BATMAN_ALIAS))
        .andExpect(jsonPath("$.superpowers.length()").value(2));
  }

  @Test
  void whenUpdateHeroWithNonExistentId_shouldReturnNotFound() throws Exception {
    Long nonExistentId = 999L;
    UpdateHeroDto updateHeroDto = HeroMother.getBatmanUpdateHeroDto();

    when(heroService.updateHeroById(eq(nonExistentId), any(UpdateHeroDto.class)))
        .thenThrow(new HeroNotFoundException(nonExistentId));

    mockMvc
        .perform(
            put("/heroes/{id}", nonExistentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateHeroDto)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errors[0].message").value("Herói com id '999' não encontrado"));
  }

  @Test
  void whenUpdateHeroWithDuplicateHeroName_shouldReturnConflict() throws Exception {
    UpdateHeroDto updateHeroDto = HeroMother.getBatmanUpdateHeroDto();

    when(heroService.updateHeroById(eq(HeroMother.BATMAN_ID), any(UpdateHeroDto.class)))
        .thenThrow(new HeroNameConflictException(HeroMother.BATMAN_ALIAS));

    mockMvc
        .perform(
            put("/heroes/{id}", HeroMother.BATMAN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateHeroDto)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.errors[0].message").value("Já existe um herói com o nome 'Batman'"));
  }

  @Test
  void whenDeleteHeroWithValidId_shouldReturnNoContent() throws Exception {
    mockMvc.perform(delete("/heroes/{id}", HeroMother.BATMAN_ID)).andExpect(status().isNoContent());

    verify(heroService).deleteHeroById(HeroMother.BATMAN_ID);
  }

  @Test
  void whenDeleteHeroWithNonExistentId_shouldReturnNotFound() throws Exception {
    Long nonExistentId = 999L;

    doThrow(new HeroNotFoundException(nonExistentId))
        .when(heroService)
        .deleteHeroById(nonExistentId);

    mockMvc
        .perform(delete("/heroes/{id}", nonExistentId))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errors[0].message").value("Herói com id '999' não encontrado"));
  }

  @Test
  void whenUseUnsupportedHttpMethod_shouldReturnMethodNotAllowed() throws Exception {
    mockMvc
        .perform(patch("/heroes"))
        .andExpect(status().isMethodNotAllowed())
        .andExpect(jsonPath("$.errors[0]").exists());
  }
}
