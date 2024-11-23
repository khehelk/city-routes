package ru.khehelk.cityroutes.adminservice.service.mapper;

import org.springframework.stereotype.Service;
import ru.khehelk.cityroutes.adminservice.domain.City;
import ru.khehelk.cityroutes.adminservice.service.dto.CityCreateDto;
import ru.khehelk.cityroutes.adminservice.service.dto.CityDto;

@Service
public class CityMapper {

    public CityDto toDto(City entity) {
        return new CityDto(
            entity.getCode(),
            entity.getName()
        );
    }

    public City toEntity(CityCreateDto city) {
        City cityEntity = new City();
        cityEntity.setCode(city.code());
        cityEntity.setName(city.name());
        return cityEntity;
    }

}
