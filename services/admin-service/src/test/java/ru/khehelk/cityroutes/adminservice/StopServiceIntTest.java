package ru.khehelk.cityroutes.adminservice;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.khehelk.cityroutes.adminservice.app.AdminServiceApplication;
import ru.khehelk.cityroutes.adminservice.config.PostgresContainerInitializer;
import ru.khehelk.cityroutes.adminservice.repository.CityRepository;
import ru.khehelk.cityroutes.adminservice.repository.RouteStopsRepository;
import ru.khehelk.cityroutes.adminservice.service.StopService;
import ru.khehelk.cityroutes.businesslogic.repository.RouteRepository;
import ru.khehelk.cityroutes.businesslogic.repository.StopRepository;
import ru.khehelk.cityroutes.domain.dto.StopCreateDto;
import ru.khehelk.cityroutes.domain.dto.StopDto;
import ru.khehelk.cityroutes.domain.dto.StopUpdateDto;
import ru.khehelk.cityroutes.domain.model.City;
import ru.khehelk.cityroutes.domain.model.Stop;

@SpringBootTest(classes = AdminServiceApplication.class)
@ContextConfiguration(initializers = PostgresContainerInitializer.class)
class StopServiceIntTest {

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StopService stopService;
    @Autowired
    private RouteStopsRepository routeStopsRepository;
    @Autowired
    private RouteRepository routeRepository;

    @BeforeEach
    void setUp() {
        routeStopsRepository.deleteAll();
        routeRepository.deleteAll();
        stopRepository.deleteAll();
        cityRepository.deleteAll();
        City city = new City();
        city.setRegionCode(73);
        city.setName("Ульяновск");
        cityRepository.save(city);
    }

    @Test
    void createAndSaveStop_Success() {
        Long cityId = cityRepository.findAll().getFirst().getId();
        StopCreateDto dto = new StopCreateDto(cityId, "Аблукова");
        stopService.createAndSaveStop(dto);
        List<Stop> stops = stopRepository.findAll();
        assertThat(stops).hasSize(1);
        assertThat(stops.getFirst().getName()).isEqualTo("Аблукова");
        assertThat(stops.getFirst().getCity().getId()).isEqualTo(cityId);
    }

    @Test
    void createAndSaveStop_IllegalArgumentException() {
        Long cityId = cityRepository.findAll().getFirst().getId();
        StopCreateDto dto = new StopCreateDto(cityId, "Аблукова");
        stopService.createAndSaveStop(dto);

        try {
            stopService.createAndSaveStop(dto);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
            assertThat(e).hasMessage("Такая остановка же существует");
        }
    }

    @Test
    void updateStop_Success() {
        Long cityId = cityRepository.findAll().getFirst().getId();
        StopCreateDto dto = new StopCreateDto(cityId, "Аблукова");
        stopService.createAndSaveStop(dto);
        Long stopId = stopRepository.findAll().getFirst().getId();
        StopUpdateDto updateDto = new StopUpdateDto("Уютная");
        stopService.updateStop(stopId, updateDto);

        List<Stop> stops = stopRepository.findAll();
        assertThat(stops).hasSize(1);
        assertThat(stops.getFirst().getName()).isEqualTo("Уютная");
        assertThat(stops.getFirst().getCity().getId()).isEqualTo(cityId);
    }

    @Test
    void deleteStop_Success() {
        Long cityId = cityRepository.findAll().getFirst().getId();
        StopCreateDto dto = new StopCreateDto(cityId, "Аблукова");
        stopService.createAndSaveStop(dto);
        Long stopId = stopRepository.findAll().getFirst().getId();
        stopService.deleteStop(stopId);
        List<Stop> stops = stopRepository.findAll();
        assertThat(stops).isEmpty();
    }

    @Test
    void findAllByCityId_Success() {
        Long cityId = cityRepository.findAll().getFirst().getId();
        StopCreateDto dto = new StopCreateDto(cityId, "Аблукова");
        stopService.createAndSaveStop(dto);
        List<StopDto> stops = stopService.findAllBy(cityId);
        assertThat(stops).hasSize(1);
        assertThat(stops.getFirst().name()).isEqualTo("Аблукова");
        assertThat(stops.getFirst().cityId()).isEqualTo(cityId);
    }

}
