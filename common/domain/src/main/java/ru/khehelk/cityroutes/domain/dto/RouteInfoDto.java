package ru.khehelk.cityroutes.domain.dto;

import java.util.Map;

public record RouteInfoDto(
    Long id,
    int number,
    int frequencyRangeStart,
    int frequencyRangeEnd,
    boolean isFavourite,
    Map<Integer, StopDto> stops
) {
}
