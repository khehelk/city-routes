package ru.khehelk.cityroutes.domain.dto;

import java.util.Map;

public record RouteUpdateDto(
    int frequencyRangeStart,
    int frequencyRangeEnd,
    Map<Integer, Long> stops
) {

}
