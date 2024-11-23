package ru.khehelk.cityroutes.adminservice.service;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khehelk.cityroutes.adminservice.domain.City;
import ru.khehelk.cityroutes.adminservice.repository.CityRepository;
import ru.khehelk.cityroutes.adminservice.service.dto.CityCreateDto;
import ru.khehelk.cityroutes.adminservice.service.dto.CityDto;
import ru.khehelk.cityroutes.adminservice.service.dto.CityUpdateDto;
import ru.khehelk.cityroutes.adminservice.service.mapper.CityMapper;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    @Transactional
    public void createAndSaveCity(CityCreateDto city) {
        cityRepository.save(cityMapper.toEntity(city));
    }

    @Transactional
    public void updateCity(Integer code,
                           CityUpdateDto city) {
        City cityEntity = cityRepository.findById(code)
            .orElseThrow(EntityNotFoundException::new);
        cityEntity.setName(city.name().toUpperCase());
        cityRepository.save(cityEntity);
    }

    @Transactional
    public void deleteCity(Integer code) {
        cityRepository.deleteById(code);
    }

    @Transactional(readOnly = true)
    public Page<CityDto> findAllBy(Pageable pageable) {
        Page<City> cityPage = cityRepository.findAll(pageable);
        return new PageImpl<>(
            cityPage.getContent().stream().map(cityMapper::toDto).toList(),
            cityPage.getPageable(),
            cityPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<CityDto> findAllBy(String searchTerm, Pageable pageable) {
        Page<City> cityPage = cityRepository.findByNameContaining(searchTerm, pageable);
        return new PageImpl<>(
            cityPage.getContent().stream().map(cityMapper::toDto).toList(),
            cityPage.getPageable(),
            cityPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public City findByCode(int code) {
        return cityRepository.findById(code)
            .orElseThrow(EntityNotFoundException::new);
    }

}
