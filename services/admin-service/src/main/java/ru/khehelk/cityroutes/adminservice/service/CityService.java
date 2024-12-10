package ru.khehelk.cityroutes.adminservice.service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khehelk.cityroutes.domain.model.City;
import ru.khehelk.cityroutes.adminservice.repository.CityRepository;
import ru.khehelk.cityroutes.domain.dto.CityCreateDto;
import ru.khehelk.cityroutes.domain.dto.CityDto;
import ru.khehelk.cityroutes.domain.dto.CityUpdateDto;
import ru.khehelk.cityroutes.domain.mapper.CityMapper;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    @Transactional
    public void createAndSaveCity(CityCreateDto city) {
        if (cityRepository.existsByRegionCode(city.regionCode())) {
            throw new IllegalArgumentException("Такой город уже существует");
        }
        cityRepository.save(cityMapper.toEntity(city));
    }

    @Transactional
    public void updateCity(Long id,
                           CityUpdateDto city) {
        City cityEntity = cityRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);
        cityEntity.setName(city.name().toUpperCase());
        cityRepository.save(cityEntity);
    }

    @Transactional
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public City findById(Long id) {
        return cityRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Город с таким id не найден"));
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
