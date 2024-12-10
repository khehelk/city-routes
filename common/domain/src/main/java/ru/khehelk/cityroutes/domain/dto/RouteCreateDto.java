package ru.khehelk.cityroutes.domain.dto;

import java.util.Map;

public record RouteCreateDto(
    Long cityId,
    int number,
    int frequencyRangeStart,
    int frequencyRangeEnd,
    Map<Integer, Long> stops
) {
}
