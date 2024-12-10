package ru.khehelk.cityroutes.domain.dto;

import java.util.Map;

public record RouteUpdateStopsDto(
    Map<Integer, Long> stopIds
) {
}
