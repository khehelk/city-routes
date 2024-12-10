package ru.khehelk.cityroutes.adminservice.service.mapper;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khehelk.cityroutes.domain.mapper.RouteSimpleMapper;
import ru.khehelk.cityroutes.domain.mapper.StopSimpleMapper;
import ru.khehelk.cityroutes.domain.model.Route;
import ru.khehelk.cityroutes.domain.model.RouteStops;
import ru.khehelk.cityroutes.adminservice.service.CityService;
import ru.khehelk.cityroutes.domain.dto.RouteCreateDto;
import ru.khehelk.cityroutes.domain.dto.RouteDto;
import ru.khehelk.cityroutes.domain.dto.RouteInfoDto;
import ru.khehelk.cityroutes.domain.dto.StopDto;

@Service
@RequiredArgsConstructor
public class RouteMapper {

    private final CityService cityService;

    private final RouteSimpleMapper routeSimpleMapper;

    private final StopSimpleMapper stopSimpleMapper;

    public Route toEntity(RouteCreateDto route) {
        Route routeEntity = new Route();
        routeEntity.setCity(cityService.findById(route.cityId()));
        routeEntity.setNumber(route.number());
        routeEntity.setFrequencyRangeStart(route.frequencyRangeStart());
        routeEntity.setFrequencyRangeEnd(route.frequencyRangeEnd());
        return routeEntity;
    }

    public RouteInfoDto toInfoDto(Route route) {
        return new RouteInfoDto(
            route.getId(),
            route.getNumber(),
            route.getFrequencyRangeStart(),
            route.getFrequencyRangeEnd(),
            createStopsMap(route)
        );
    }

    private Map<Integer, StopDto> createStopsMap(Route route) {
        Map<Integer, StopDto> stopsMap = new HashMap<>();
        for (RouteStops routeStop : route.getStops()) {
            stopsMap.put(
                routeStop.getStopOrder(),
                stopSimpleMapper.toDto(routeStop.getStop()));
        }
        return stopsMap;
    }
}
