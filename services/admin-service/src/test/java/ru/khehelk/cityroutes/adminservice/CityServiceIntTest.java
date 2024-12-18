package ru.khehelk.cityroutes.adminservice;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.khehelk.cityroutes.adminservice.app.AdminServiceApplication;
import ru.khehelk.cityroutes.adminservice.config.PostgresContainerInitializer;
import ru.khehelk.cityroutes.adminservice.repository.CityRepository;
import ru.khehelk.cityroutes.adminservice.repository.RouteStopsRepository;
import ru.khehelk.cityroutes.adminservice.service.CityService;
import ru.khehelk.cityroutes.businesslogic.repository.RouteRepository;
import ru.khehelk.cityroutes.businesslogic.repository.StopRepository;
import ru.khehelk.cityroutes.domain.dto.CityCreateDto;
import ru.khehelk.cityroutes.domain.dto.CityDto;
import ru.khehelk.cityroutes.domain.dto.CityUpdateDto;
import ru.khehelk.cityroutes.domain.model.City;

@SpringBootTest(classes = AdminServiceApplication.class)
@ContextConfiguration(initializers = PostgresContainerInitializer.class)
class CityServiceIntTest {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private RouteStopsRepository routeStopsRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private StopRepository stopRepository;

    @BeforeEach
    void setUp() {
        routeStopsRepository.deleteAll();
        routeRepository.deleteAll();
        stopRepository.deleteAll();
        cityRepository.deleteAll();
    }

    @Test
    void createCity_Success() {
        CityCreateDto dto = new CityCreateDto(73, "Ульяновск");
        cityService.createAndSaveCity(dto);
        assertThat(cityRepository.findAll()).hasSize(1);
    }

    @Test
    void createCity_IllegalArgumentException() {
        CityCreateDto dto = new CityCreateDto(73, "Ульяновск");
        cityService.createAndSaveCity(dto);

        try {
            cityService.createAndSaveCity(dto);
        } catch (Exception e) {
            assertThat(e.getClass()).isEqualTo(IllegalArgumentException.class);
            assertThat(e).hasMessage("Такой город уже существует");
        }
    }

    @Test
    void updateCity_Success() {
        CityCreateDto dto = new CityCreateDto(73, "Ульяновск");
        CityUpdateDto updateDto = new CityUpdateDto("Димитровград");
        cityService.createAndSaveCity(dto);
        Long id = cityRepository.findAll().getFirst().getId();
        cityService.updateCity(id, updateDto);
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(1);
        assertThat(cityList.getFirst().getName()).isEqualTo("ДИМИТРОВГРАД");
        assertThat(cityList.getFirst().getId()).isEqualTo(id);
        assertThat(cityList.getFirst().getRegionCode()).isEqualTo(73);
    }

    @Test
    void updateCity_EntityNotFoundException() {
        CityCreateDto dto = new CityCreateDto(73, "Ульяновск");
        CityUpdateDto updateDto = new CityUpdateDto("Димитровград");
        cityService.createAndSaveCity(dto);
        Long id = cityRepository.findAll().getFirst().getId();
        try {
            cityService.updateCity(id + 1, updateDto);
        } catch (Exception e) {
            assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    @Test
    void deleteCity_Success() {
        CityCreateDto dto = new CityCreateDto(73, "Ульяновск");
        cityService.createAndSaveCity(dto);
        Long id = cityRepository.findAll().getFirst().getId();
        cityService.deleteCity(id);
        assertThat(cityRepository.findAll()).isEmpty();
    }

    @Test
    void findAllByRegionCode_Success() {
        CityCreateDto dto = new CityCreateDto(73, "Ульяновск");
        cityService.createAndSaveCity(dto);
        List<CityDto> cityList = cityService.findAllBy(73);
        assertThat(cityList).hasSize(1);
    }

}
