package ru.khehelk.cityroutes.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khehelk.cityroutes.domain.dto.RouteDto;
import ru.khehelk.cityroutes.domain.model.Route;

@Service
@RequiredArgsConstructor
public class RouteSimpleMapper {

    public RouteDto toDto(Route route) {
        return new RouteDto(
            route.getId(),
            route.getNumber(),
            route.getFrequencyRangeStart(),
            route.getFrequencyRangeEnd()
        );
    }

}
