package br.com.superheroes.superhero.superpower.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.superheroes.shared.exception.GlobalExceptionHandler;
import br.com.superheroes.superhero.superpower.application.dto.CreateSuperpowerDto;
import br.com.superheroes.superhero.superpower.application.dto.SuperpowerOutputDto;
import br.com.superheroes.superhero.superpower.application.dto.UpdateSuperpowerDto;
import br.com.superheroes.superhero.superpower.domain.exception.InvalidSuperpowerException;
import br.com.superheroes.superhero.superpower.domain.exception.SuperpowerNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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
class SuperpowerControllerTest {
  private final ObjectMapper objectMapper = new ObjectMapper();
  @InjectMocks private SuperpowerController controller;
  @Mock private SuperpowerService service;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    objectMapper.findAndRegisterModules();
  }

  @Test
  void whenCreateSuperpowerWithValidData_shouldReturnCreated() throws Exception {
    CreateSuperpowerDto request = new CreateSuperpowerDto("Voo", "Capacidade de voar");
    SuperpowerOutputDto response = new SuperpowerOutputDto(1L, "Voo", "Capacidade de voar");

    when(service.createSuperpower(any(CreateSuperpowerDto.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/superpowers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Voo"))
        .andExpect(jsonPath("$.description").value("Capacidade de voar"));

    verify(service).createSuperpower(any(CreateSuperpowerDto.class));
  }

  @Test
  void whenCreateSuperpowerWithInvalidData_shouldReturnBadRequest() throws Exception {
    CreateSuperpowerDto invalid = new CreateSuperpowerDto("", "");

    mockMvc
        .perform(
            post("/superpowers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors").isArray())
        .andExpect(jsonPath("$.status").value(400));
  }

  @Test
  void whenGetAllSuperpowers_shouldReturnList() throws Exception {
    SuperpowerOutputDto sp1 = new SuperpowerOutputDto(1L, "Voo", "Capacidade de voar");
    SuperpowerOutputDto sp2 = new SuperpowerOutputDto(2L, "Força", "Super força");
    when(service.getSuperpowers()).thenReturn(List.of(sp1, sp2));

    mockMvc
        .perform(get("/superpowers"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].name").value("Voo"))
        .andExpect(jsonPath("$[1].name").value("Força"));
  }

  @Test
  void whenGetAllSuperpowersWithEmptyList_shouldReturnEmptyList() throws Exception {
    when(service.getSuperpowers()).thenReturn(List.of());

    mockMvc
        .perform(get("/superpowers"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  void whenGetSuperpowerByIdWithValidId_shouldReturnSuperpower() throws Exception {
    Long id = 1L;
    SuperpowerOutputDto response = new SuperpowerOutputDto(id, "Voo", "Capacidade de voar");

    when(service.getSuperpowerById(id)).thenReturn(response);

    mockMvc
        .perform(get("/superpowers/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.name").value("Voo"));
  }

  @Test
  void whenGetSuperpowerByIdWithNonExistentId_shouldReturnNotFound() throws Exception {
    Long id = 999L;
    when(service.getSuperpowerById(id)).thenThrow(new SuperpowerNotFoundException(id));

    mockMvc
        .perform(get("/superpowers/{id}", id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errors[0].message").value("Superpoder com id '999' não encontrado"));
  }

  @Test
  void whenUpdateSuperpowerWithValidData_shouldReturnUpdated() throws Exception {
    Long id = 1L;
    UpdateSuperpowerDto request = new UpdateSuperpowerDto("Voo", "Capacidade de voar muito rápido");
    SuperpowerOutputDto response =
        new SuperpowerOutputDto(id, "Voo", "Capacidade de voar muito rápido");

    when(service.updateSuperpowerById(eq(id), any(UpdateSuperpowerDto.class))).thenReturn(response);

    mockMvc
        .perform(
            put("/superpowers/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.description").value("Capacidade de voar muito rápido"));

    verify(service).updateSuperpowerById(eq(id), any(UpdateSuperpowerDto.class));
  }

  @Test
  void whenUpdateSuperpowerWithNonExistentId_shouldReturnNotFound() throws Exception {
    Long id = 999L;
    UpdateSuperpowerDto request = new UpdateSuperpowerDto("Voo", "Descrição inválida");

    when(service.updateSuperpowerById(eq(id), any(UpdateSuperpowerDto.class)))
        .thenThrow(new SuperpowerNotFoundException(id));

    mockMvc
        .perform(
            put("/superpowers/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errors[0].message").value("Superpoder com id '999' não encontrado"));
  }

  @Test
  void whenDeleteSuperpowerWithValidId_shouldReturnNoContent() throws Exception {
    Long id = 1L;

    mockMvc.perform(delete("/superpowers/{id}", id)).andExpect(status().isNoContent());

    verify(service).deleteSuperpowerById(id);
  }

  @Test
  void whenDeleteSuperpowerWithNonExistentId_shouldReturnNotFound() throws Exception {
    Long id = 999L;
    doThrow(new SuperpowerNotFoundException(id)).when(service).deleteSuperpowerById(id);

    mockMvc
        .perform(delete("/superpowers/{id}", id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errors[0].message").value("Superpoder com id '999' não encontrado"));
  }

  @Test
  void whenUseUnsupportedHttpMethod_shouldReturnMethodNotAllowed() throws Exception {
    mockMvc
        .perform(patch("/superpowers"))
        .andExpect(status().isMethodNotAllowed())
        .andExpect(jsonPath("$.errors[0].message").exists());
  }

  @Test
  void whenCreateSuperpowerWithInvalidSuperpower_shouldReturnBadRequest() throws Exception {
    CreateSuperpowerDto request = new CreateSuperpowerDto("Voo", "Capacidade de voar");
    when(service.createSuperpower(any(CreateSuperpowerDto.class)))
        .thenThrow(new InvalidSuperpowerException(List.of(5L)));

    mockMvc
        .perform(
            post("/superpowers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.errors[0].message")
                .value("Superpoder(es) não encontrado(s) para ID(s): [5]"));
  }
}
