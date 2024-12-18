package ru.khehelk.cityroutes.adminservice.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khehelk.cityroutes.adminservice.service.CityService;
import ru.khehelk.cityroutes.domain.dto.RouteCreateDto;
import ru.khehelk.cityroutes.domain.mapper.RouteSimpleMapper;
import ru.khehelk.cityroutes.domain.mapper.StopSimpleMapper;
import ru.khehelk.cityroutes.domain.model.Route;

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

}
