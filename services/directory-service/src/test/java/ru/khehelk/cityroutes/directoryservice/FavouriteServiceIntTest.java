package ru.khehelk.cityroutes.directoryservice;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import ru.khehelk.cityroutes.businesslogic.repository.StopRepository;
import ru.khehelk.cityroutes.directoryservice.app.DirectoryServiceApplication;
import ru.khehelk.cityroutes.directoryservice.config.PostgresContainerInitializer;
import ru.khehelk.cityroutes.directoryservice.domain.Favourite;
import ru.khehelk.cityroutes.directoryservice.domain.RouteEntity;
import ru.khehelk.cityroutes.directoryservice.domain.User;
import ru.khehelk.cityroutes.directoryservice.repository.CityRepository;
import ru.khehelk.cityroutes.directoryservice.repository.DirectoryRouteRepository;
import ru.khehelk.cityroutes.directoryservice.repository.FavouriteRepository;
import ru.khehelk.cityroutes.directoryservice.repository.UserRepository;
import ru.khehelk.cityroutes.directoryservice.service.RouteService;
import ru.khehelk.cityroutes.domain.model.City;
import ru.khehelk.cityroutes.domain.model.Role;
import ru.khehelk.cityroutes.domain.model.Route;
import ru.khehelk.cityroutes.domain.model.Stop;

@SpringBootTest(classes = DirectoryServiceApplication.class)
@ContextConfiguration(initializers = PostgresContainerInitializer.class)
class FavouriteServiceIntTest {

    @Autowired
    private DirectoryRouteRepository routeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private RouteService routeService;

    @BeforeEach
    public void setUp() {
        favouriteRepository.deleteAll();
        routeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void addInFavourite() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setRole(Role.ADMIN);
        User userEntity= userRepository.save(user);
        City city = new City();
        city.setRegionCode(73);
        city.setName("Ульяновск");
        City cityEntity = cityRepository.save(city);
        RouteEntity route = new RouteEntity();
        route.setNumber(4);
        route.setFrequencyRangeStart(10);
        route.setFrequencyRangeEnd(15);
        route.setCity(cityEntity);
        RouteEntity routeEntity = routeRepository.save(route);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userEntity, null));

        routeService.addInFavourite(routeEntity.getId());
        List<Favourite> favs = favouriteRepository.findAll();
        assertThat(favs).hasSize(1);
    }

    @Test
    void removeFromFavourite() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setRole(Role.ADMIN);
        User userEntity= userRepository.save(user);
        City city = new City();
        city.setRegionCode(73);
        city.setName("Ульяновск");
        City cityEntity = cityRepository.save(city);
        RouteEntity route = new RouteEntity();
        route.setNumber(4);
        route.setFrequencyRangeStart(10);
        route.setFrequencyRangeEnd(15);
        route.setCity(cityEntity);
        RouteEntity routeEntity = routeRepository.save(route);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userEntity, null));

        routeService.addInFavourite(routeEntity.getId());

        routeService.removeFromFavourite(routeEntity.getId());
        List<Favourite> favs = favouriteRepository.findAll();
        assertThat(favs).hasSize(0);
    }

}
