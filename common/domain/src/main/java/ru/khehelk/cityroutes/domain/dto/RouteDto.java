package ru.khehelk.cityroutes.domain.dto;

public record RouteDto(
    Long id,
    int number,
    int frequencyRangeStart,
    int frequencyRangeEnd
) {
}
