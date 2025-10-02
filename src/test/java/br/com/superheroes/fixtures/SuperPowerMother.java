package br.com.superheroes.fixtures;

import br.com.superheroes.superhero.superpower.application.dto.SuperpowerOutputDto;
import java.util.Set;

public class SuperPowerMother {
  public static final Long INTELLIGENCE_ID = 1L;
  public static final String INTELLIGENCE_NAME = "Inteligência";
  public static final String INTELLIGENCE_DESCRIPTION = "Capacidade intelectual acima da média";

  public static final Long MARTIAL_ARTS_ID = 2L;
  public static final String MARTIAL_ARTS_NAME = "Artes Marciais";
  public static final String MARTIAL_ARTS_DESCRIPTION = "Mestre em várias técnicas de combate";

  public static final Long SUPER_STRENGTH_ID = 3L;
  public static final String SUPER_STRENGTH_NAME = "Super Força";
  public static final String SUPER_STRENGTH_DESCRIPTION = "Força física sobre-humana";

  public static final Long FLIGHT_ID = 4L;
  public static final String FLIGHT_NAME = "Voo";
  public static final String FLIGHT_DESCRIPTION = "Capacidade de voar";

  public static final Long HEAT_VISION_ID = 5L;
  public static final String HEAT_VISION_NAME = "Visão de Calor";
  public static final String HEAT_VISION_DESCRIPTION = "Emitir raios de calor pelos olhos";

  public static final Long SPIDER_SENSE_ID = 6L;
  public static final String SPIDER_SENSE_NAME = "Sentido Aranha";
  public static final String SPIDER_SENSE_DESCRIPTION = "Sentido de perigo aguçado";

  public static Set<SuperpowerOutputDto> getBatmanSuperpowers() {
    return Set.of(
        new SuperpowerOutputDto(INTELLIGENCE_ID, INTELLIGENCE_NAME, INTELLIGENCE_DESCRIPTION),
        new SuperpowerOutputDto(MARTIAL_ARTS_ID, MARTIAL_ARTS_NAME, MARTIAL_ARTS_DESCRIPTION));
  }

  public static Set<SuperpowerOutputDto> getSingleIntelligenceSuperpower() {
    return Set.of(
        new SuperpowerOutputDto(INTELLIGENCE_ID, INTELLIGENCE_NAME, INTELLIGENCE_DESCRIPTION));
  }

  public static Set<SuperpowerOutputDto> getSupermanSuperpowers() {
    return Set.of(
        new SuperpowerOutputDto(SUPER_STRENGTH_ID, SUPER_STRENGTH_NAME, SUPER_STRENGTH_DESCRIPTION),
        new SuperpowerOutputDto(FLIGHT_ID, FLIGHT_NAME, FLIGHT_DESCRIPTION),
        new SuperpowerOutputDto(HEAT_VISION_ID, HEAT_VISION_NAME, HEAT_VISION_DESCRIPTION));
  }

  public static Set<SuperpowerOutputDto> getSpidermanSuperpowers() {
    return Set.of(
        new SuperpowerOutputDto(SPIDER_SENSE_ID, SPIDER_SENSE_NAME, SPIDER_SENSE_DESCRIPTION));
  }
}
