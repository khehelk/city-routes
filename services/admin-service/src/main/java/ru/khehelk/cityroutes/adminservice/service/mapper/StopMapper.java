package ru.khehelk.cityroutes.adminservice.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khehelk.cityroutes.adminservice.service.CityService;
import ru.khehelk.cityroutes.domain.dto.StopCreateDto;
import ru.khehelk.cityroutes.domain.model.Stop;

@Service
@RequiredArgsConstructor
public class StopMapper {

    private final CityService cityService;

    public Stop toEntity(StopCreateDto stop) {
        Stop stopEntity = new Stop();
        stopEntity.setCity(cityService.findById(stop.cityId()));
        stopEntity.setName(stop.name());
        return stopEntity;
    }

}
