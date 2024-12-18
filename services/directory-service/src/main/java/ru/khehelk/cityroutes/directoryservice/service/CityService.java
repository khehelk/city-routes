package ru.khehelk.cityroutes.directoryservice.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khehelk.cityroutes.directoryservice.repository.CityRepository;
import ru.khehelk.cityroutes.domain.dto.CityDto;
import ru.khehelk.cityroutes.domain.mapper.CityMapper;
import ru.khehelk.cityroutes.domain.model.City;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    @Transactional(readOnly = true)
    public Page<CityDto> findAllBy(Pageable pageable) {
        Page<City> cityPage = cityRepository.findAll(pageable);
        return new PageImpl<>(
            cityPage.getContent().stream().map(cityMapper::toDto).toList(),
            cityPage.getPageable(),
            cityPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<CityDto> findAllBy(Integer regionCode) {
        List<City> cities = cityRepository.findAllByRegionCode(regionCode);
        return cities.stream().map(cityMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Page<CityDto> findAllBy(String searchTerm, Pageable pageable) {
        Page<City> cityPage = cityRepository.findByNameContaining(searchTerm, pageable);
        return new PageImpl<>(
            cityPage.getContent().stream().map(cityMapper::toDto).toList(),
            cityPage.getPageable(),
            cityPage.getTotalElements());
    }

}
