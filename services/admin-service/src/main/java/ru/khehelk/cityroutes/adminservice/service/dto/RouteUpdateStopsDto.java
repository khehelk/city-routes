package ru.khehelk.cityroutes.adminservice.service.dto;

import java.util.Map;

public record RouteUpdateStopsDto(
    Map<Integer, Long> stopIds
) {
}
