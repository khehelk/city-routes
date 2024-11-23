package ru.khehelk.cityroutes.adminservice.service.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khehelk.cityroutes.adminservice.domain.Route;
import ru.khehelk.cityroutes.adminservice.domain.RouteStops;
import ru.khehelk.cityroutes.adminservice.service.CityService;
import ru.khehelk.cityroutes.adminservice.service.dto.RouteCreateDto;
import ru.khehelk.cityroutes.adminservice.service.dto.RouteDto;
import ru.khehelk.cityroutes.adminservice.service.dto.RouteInfoDto;
import ru.khehelk.cityroutes.adminservice.service.dto.StopDto;

@Service
@RequiredArgsConstructor
public class RouteMapper {

    private final CityService cityService;

    private final StopMapper stopMapper;

    public Route toEntity(RouteCreateDto route) {
        Route routeEntity = new Route();
        routeEntity.setCity(cityService.findByCode(route.cityCode()));
        routeEntity.setNumber(route.number());
        routeEntity.setFrequencyRangeStart(route.frequencyRangeStart());
        routeEntity.setFrequencyRangeEnd(route.frequencyRangeEnd());
        return routeEntity;
    }

    public RouteDto toDto(Route route) {
        return new RouteDto(
            route.getId(),
            route.getNumber(),
            route.getFrequencyRangeStart(),
            route.getFrequencyRangeEnd()
        );
    }

    public RouteInfoDto toInfoDto(Route route) {
        return new RouteInfoDto(
            toDto(route),
            createStopsMap(route)
        );
    }

    private Map<Integer, StopDto> createStopsMap(Route route) {
        Map<Integer, StopDto> stopsMap = new HashMap<>();
        for (RouteStops routeStop : route.getStops()) {
            stopsMap.put(
                routeStop.getStopOrder(),
                stopMapper.toDto(routeStop.getStop()));
        }
        return stopsMap;
    }
}
