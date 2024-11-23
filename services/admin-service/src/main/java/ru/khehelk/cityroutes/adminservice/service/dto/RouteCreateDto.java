package ru.khehelk.cityroutes.adminservice.service.dto;

import java.util.Map;

public record RouteCreateDto(
    int cityCode,
    int number,
    int frequencyRangeStart,
    int frequencyRangeEnd,
    Map<Integer, Long> stops
) {
}
