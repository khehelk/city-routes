package ru.khehelk.cityroutes.adminservice.service.dto;

public record RouteDto(
    Long id,
    int number,
    int frequencyRangeStart,
    int frequencyRangeEnd
) {
}
