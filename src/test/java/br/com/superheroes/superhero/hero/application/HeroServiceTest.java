package br.com.superheroes.superhero.hero.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import br.com.superheroes.fixtures.HeroMother;
import br.com.superheroes.fixtures.SuperPowerMother;
import br.com.superheroes.superhero.hero.application.dto.CreateHeroDto;
import br.com.superheroes.superhero.hero.application.dto.HeroOutputDto;
import br.com.superheroes.superhero.hero.application.dto.UpdateHeroDto;
import br.com.superheroes.superhero.hero.application.mapper.HeroMapper;
import br.com.superheroes.superhero.hero.domain.HeroDomainService;
import br.com.superheroes.superhero.hero.domain.exception.HeroNameConflictException;
import br.com.superheroes.superhero.hero.domain.exception.HeroNotFoundException;
import br.com.superheroes.superhero.hero.domain.model.Hero;
import br.com.superheroes.superhero.superpower.domain.SuperpowerDomainService;
import br.com.superheroes.superhero.superpower.domain.model.Superpower;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HeroServiceTest {
  @Mock private SuperpowerDomainService superpowerDomainService;
  @Mock private HeroDomainService heroDomainService;
  @InjectMocks private HeroService heroService;
  @Mock private HeroMapper mapper;

  private CreateHeroDto createBatmanDto;
  private UpdateHeroDto updateBatmanDto;
  private HeroOutputDto batmanOutput;
  private Hero batmanDomain;

  @BeforeEach
  void setUp() {
    createBatmanDto = HeroMother.getBatmanCreateHeroDto();
    updateBatmanDto = HeroMother.getBatmanUpdateHeroDto();
    batmanOutput =
        HeroMother.getBatmanCreatedHeroOutputDto(SuperPowerMother.getBatmanSuperpowers());
    batmanDomain =
        new Hero(
            HeroMother.BATMAN_ID,
            HeroMother.BATMAN_NAME,
            HeroMother.BATMAN_ALIAS,
            HeroMother.BATMAN_BIRTH_DATE,
            HeroMother.BATMAN_HEIGHT,
            HeroMother.BATMAN_WEIGHT,
            Set.of());
  }

  @Test
  void whenCreateHero_shouldReturnCreatedHero() {
    Set<Superpower> superpowers = Set.of(mock(Superpower.class), mock(Superpower.class));

    when(superpowerDomainService.findAllByIdsOrThrow(createBatmanDto.getSuperpowers()))
        .thenReturn(superpowers);
    when(mapper.toDomain(createBatmanDto, superpowers)).thenReturn(batmanDomain);
    when(heroDomainService.createHero(batmanDomain)).thenReturn(batmanDomain);
    when(mapper.toOutput(batmanDomain)).thenReturn(batmanOutput);

    HeroOutputDto result = heroService.createHero(createBatmanDto);

    assertThat(result).isEqualTo(batmanOutput);
    verify(heroDomainService).createHero(batmanDomain);
  }

  @Test
  void whenGetHeroes_shouldReturnAllHeroes() {
    List<Hero> heroes = List.of(batmanDomain);

    when(heroDomainService.findAll()).thenReturn(heroes);
    when(mapper.toOutput(heroes)).thenReturn(List.of(batmanOutput));

    List<HeroOutputDto> result = heroService.getHeroes();

    assertThat(result).containsExactly(batmanOutput);
    verify(heroDomainService).findAll();
  }

  @Test
  void whenGetHeroById_shouldReturnHero() {
    when(heroDomainService.findByIdOrThrow(HeroMother.BATMAN_ID)).thenReturn(batmanDomain);
    when(mapper.toOutput(batmanDomain)).thenReturn(batmanOutput);

    HeroOutputDto result = heroService.getHeroById(HeroMother.BATMAN_ID);

    assertThat(result).isEqualTo(batmanOutput);
  }

  @Test
  void whenGetHeroById_andNotFound_shouldThrow() {
    when(heroDomainService.findByIdOrThrow(99L)).thenThrow(new HeroNotFoundException(99L));

    assertThatThrownBy(() -> heroService.getHeroById(99L))
        .isInstanceOf(HeroNotFoundException.class)
        .hasMessageContaining("99");
  }

  @Test
  void whenUpdateHero_shouldReturnUpdatedHero() {
    Set<Superpower> superpowers = Set.of(mock(Superpower.class), mock(Superpower.class));
    Hero updatedDomain =
        new Hero(
            HeroMother.BATMAN_ID,
            HeroMother.BATMAN_NAME,
            HeroMother.BATMAN_ALIAS,
            HeroMother.BATMAN_BIRTH_DATE,
            HeroMother.BATMAN_HEIGHT,
            96.0,
            superpowers);
    HeroOutputDto updatedOutput =
        HeroMother.getBatmanUpdatedHeroOutputDto(SuperPowerMother.getBatmanSuperpowers());

    when(superpowerDomainService.findAllByIdsOrThrow(updateBatmanDto.getSuperpowers()))
        .thenReturn(superpowers);
    when(mapper.toDomain(HeroMother.BATMAN_ID, updateBatmanDto, superpowers))
        .thenReturn(updatedDomain);
    when(heroDomainService.updateHero(updatedDomain)).thenReturn(updatedDomain);
    when(mapper.toOutput(updatedDomain)).thenReturn(updatedOutput);

    HeroOutputDto result = heroService.updateHeroById(HeroMother.BATMAN_ID, updateBatmanDto);

    assertThat(result).isEqualTo(updatedOutput);
  }

  @Test
  void whenUpdateHero_andNameConflict_shouldThrow() {
    Set<Superpower> superpowers = Set.of();
    Hero toUpdate = batmanDomain;

    when(superpowerDomainService.findAllByIdsOrThrow(updateBatmanDto.getSuperpowers()))
        .thenReturn(superpowers);
    when(mapper.toDomain(HeroMother.BATMAN_ID, updateBatmanDto, superpowers)).thenReturn(toUpdate);
    when(heroDomainService.updateHero(toUpdate))
        .thenThrow(new HeroNameConflictException(HeroMother.BATMAN_NAME));

    assertThatThrownBy(() -> heroService.updateHeroById(HeroMother.BATMAN_ID, updateBatmanDto))
        .isInstanceOf(HeroNameConflictException.class)
        .hasMessageContaining(HeroMother.BATMAN_NAME);
  }

  @Test
  void whenDeleteHero_shouldCallDomainService() {
    doNothing().when(heroDomainService).deleteHeroById(HeroMother.BATMAN_ID);

    heroService.deleteHeroById(HeroMother.BATMAN_ID);

    verify(heroDomainService).deleteHeroById(HeroMother.BATMAN_ID);
  }
}
