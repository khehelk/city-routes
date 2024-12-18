package ru.khehelk.cityroutes.directoryservice.service.mapper;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.khehelk.cityroutes.directoryservice.domain.RouteEntity;
import ru.khehelk.cityroutes.domain.dto.RouteInfoDto;
import ru.khehelk.cityroutes.domain.dto.StopDto;
import ru.khehelk.cityroutes.domain.mapper.StopSimpleMapper;
import ru.khehelk.cityroutes.domain.model.RouteStops;

@Service
@RequiredArgsConstructor
public class RouteMapper {

    private final StopSimpleMapper stopSimpleMapper;

    public RouteInfoDto toInfoDto(RouteEntity route) {
        boolean isFavourite = false;
        try {
            isFavourite = route.getFavourites().stream()
                .anyMatch(fav -> fav.getUser().getUsername().equals(
                    ((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal()).getUsername()));
        } catch (Exception ignored) {}
        return new RouteInfoDto(
            route.getId(),
            route.getNumber(),
            route.getFrequencyRangeStart(),
            route.getFrequencyRangeEnd(),
            isFavourite,
            createStopsMap(route)
        );
    }

    private Map<Integer, StopDto> createStopsMap(RouteEntity route) {
        Map<Integer, StopDto> stopsMap = new HashMap<>();
        for (RouteStops routeStop : route.getStops()) {
            stopsMap.put(
                routeStop.getStopOrder(),
                stopSimpleMapper.toDto(routeStop.getStop()));
        }
        return stopsMap;
    }
}
