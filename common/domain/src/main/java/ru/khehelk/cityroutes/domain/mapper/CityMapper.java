package ru.khehelk.cityroutes.domain.mapper;

import org.springframework.stereotype.Service;
import ru.khehelk.cityroutes.domain.dto.CityCreateDto;
import ru.khehelk.cityroutes.domain.dto.CityDto;
import ru.khehelk.cityroutes.domain.model.City;

@Service
public class CityMapper {

    public CityDto toDto(City entity) {
        return new CityDto(
            entity.getId(),
            entity.getRegionCode(),
            entity.getName()
        );
    }

    public City toEntity(CityCreateDto city) {
        City cityEntity = new City();
        cityEntity.setRegionCode(city.regionCode());
        cityEntity.setName(city.name().toUpperCase());
        return cityEntity;
    }

}
