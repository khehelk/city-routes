package ru.khehelk.cityroutes.directoryservice.service.mapper;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khehelk.cityroutes.domain.dto.RouteInfoDto;
import ru.khehelk.cityroutes.domain.dto.StopDto;
import ru.khehelk.cityroutes.domain.mapper.RouteSimpleMapper;
import ru.khehelk.cityroutes.domain.mapper.StopSimpleMapper;
import ru.khehelk.cityroutes.domain.model.Route;
import ru.khehelk.cityroutes.domain.model.RouteStops;

@Service
@RequiredArgsConstructor
public class RouteMapper {

    private final StopSimpleMapper stopSimpleMapper;

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
