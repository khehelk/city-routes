package ru.khehelk.cityroutes.adminservice.service.mapper;

import java.util.Collections;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khehelk.cityroutes.adminservice.domain.Stop;
import ru.khehelk.cityroutes.adminservice.service.CityService;
import ru.khehelk.cityroutes.adminservice.service.dto.StopCreateDto;
import ru.khehelk.cityroutes.adminservice.service.dto.StopDto;

@Service
@RequiredArgsConstructor
public class StopMapper {

    private final CityService cityService;

    public Stop toEntity(StopCreateDto stop) {
        Stop stopEntity = new Stop();
        stopEntity.setCity(cityService.findByCode(stop.cityCode()));
        stopEntity.setName(stop.name());
        return stopEntity;
    }

    public StopDto toDto(Stop stop) {
        return new StopDto(
            stop.getId(),
            stop.getCity().getCode(),
            stop.getName()
        );
    }

}
