package ru.khehelk.cityroutes.domain.dto;

import java.util.Map;

public record RouteInfoDto(
    Long id,
    int number,
    int frequencyRangeStart,
    int frequencyRangeEnd,
    Map<Integer, StopDto> stops
) {
}
