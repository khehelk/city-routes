package ru.khehelk.cityroutes.adminservice.service.dto;

import java.util.Map;

public record RouteInfoDto(
    RouteDto routeDto,
    Map<Integer, StopDto> stops
) {
}
