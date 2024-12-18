package ru.khehelk.cityroutes.adminservice;

import java.util.List;
import java.util.Map;

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
import ru.khehelk.cityroutes.adminservice.service.RouteService;
import ru.khehelk.cityroutes.businesslogic.repository.RouteRepository;
import ru.khehelk.cityroutes.businesslogic.repository.StopRepository;
import ru.khehelk.cityroutes.domain.dto.RouteCreateDto;
import ru.khehelk.cityroutes.domain.dto.RouteUpdateDto;
import ru.khehelk.cityroutes.domain.model.City;
import ru.khehelk.cityroutes.domain.model.Route;
import ru.khehelk.cityroutes.domain.model.RouteStops;
import ru.khehelk.cityroutes.domain.model.Stop;

@SpringBootTest(classes = AdminServiceApplication.class)
@ContextConfiguration(initializers = PostgresContainerInitializer.class)
class RouteServiceIntTest {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RouteService routeService;

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private RouteStopsRepository routeStopsRepository;

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
        Stop stop = new Stop();
        stop.setCity(cityRepository.findAll().getFirst());
        stop.setName("Аблукова");
        stopRepository.save(stop);
    }

    @Test
    void createAndSaveRoute_Success() {
        Long cityId = cityRepository.findAll().getFirst().getId();
        Long stopId = stopRepository.findAll().getFirst().getId();
        RouteCreateDto routeCreateDto = new RouteCreateDto(
            cityId,
            4,
            10,
            15,
            Map.of(1, stopId)
        );

        routeService.createAndSaveRoute(routeCreateDto);
        List<Route> routes = routeRepository.findAll();
        assertThat(routes).hasSize(1);
    }

    @Test
    void updateRoute_Success() {
        Long cityId = cityRepository.findAll().getFirst().getId();
        Long stopId = stopRepository.findAll().getFirst().getId();
        RouteCreateDto routeCreateDto = new RouteCreateDto(
            cityId,
            4,
            10,
            15,
            Map.of(1, stopId)
        );
        Stop stop = new Stop();
        stop.setCity(cityRepository.findAll().getFirst());
        stop.setName("Уютная");
        stopRepository.save(stop);
        Long newStopId = stopRepository.findAll().stream().filter(s -> !s.getId().equals(stopId))
            .findFirst().get().getId();
        routeService.createAndSaveRoute(routeCreateDto);
        List<Route> routes = routeRepository.findAll();
        RouteUpdateDto routeUpdateDto = new RouteUpdateDto(15, 20, Map.of(2, newStopId));

        routeService.updateRoute(routes.getFirst().getId(), routeUpdateDto);

        List<Route> newRoutes = routeRepository.findAll();
        List<RouteStops> routeStops = routeStopsRepository.findAll();
        assertThat(newRoutes).hasSize(1);
        assertThat(newRoutes.getFirst().getFrequencyRangeEnd()).isEqualTo(20);
        assertThat(newRoutes.getFirst().getFrequencyRangeStart()).isEqualTo(15);
        assertThat(routeStops).hasSize(1);
        assertThat(routeStops.getFirst().getStopOrder()).isEqualTo(2);
    }

    @Test
    void deleteRoute_Success() {
        Long cityId = cityRepository.findAll().getFirst().getId();
        Long stopId = stopRepository.findAll().getFirst().getId();
        RouteCreateDto routeCreateDto = new RouteCreateDto(
            cityId,
            4,
            10,
            15,
            Map.of(1, stopId)
        );

        routeService.createAndSaveRoute(routeCreateDto);
        List<Route> routes = routeRepository.findAll();
        routeService.deleteRoute(routes.getFirst().getId());
        List<Route> newRoutes = routeRepository.findAll();
        assertThat(newRoutes).isEmpty();
    }

}
