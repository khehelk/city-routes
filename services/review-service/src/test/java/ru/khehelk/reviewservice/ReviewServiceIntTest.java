package ru.khehelk.reviewservice;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import ru.khehelk.cityroutes.businesslogic.repository.RouteRepository;
import ru.khehelk.cityroutes.domain.model.City;
import ru.khehelk.cityroutes.domain.model.Role;
import ru.khehelk.cityroutes.domain.model.Route;
import ru.khehelk.cityroutes.reviewservice.app.ReviewServiceApplication;
import ru.khehelk.cityroutes.reviewservice.domain.Review;
import ru.khehelk.cityroutes.reviewservice.domain.ReviewId;
import ru.khehelk.cityroutes.reviewservice.domain.User;
import ru.khehelk.cityroutes.reviewservice.repository.CityRepository;
import ru.khehelk.cityroutes.reviewservice.repository.ReviewRepository;
import ru.khehelk.cityroutes.reviewservice.repository.UserRepository;
import ru.khehelk.cityroutes.reviewservice.service.ReviewService;
import ru.khehelk.cityroutes.reviewservice.service.dto.ReviewCreationDto;
import ru.khehelk.reviewservice.config.PostgresContainerInitializer;

@SpringBootTest(classes = ReviewServiceApplication.class)
@ContextConfiguration(initializers = PostgresContainerInitializer.class)
public class ReviewServiceIntTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void addReview() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setRole(Role.ADMIN);
        User userEntity= userRepository.save(user);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userEntity, null));
        City city = new City();
        city.setRegionCode(73);
        city.setName("Ульяновск");
        City cityEntity = cityRepository.save(city);
        Route route = new Route();
        route.setNumber(4);
        route.setFrequencyRangeStart(10);
        route.setFrequencyRangeEnd(15);
        route.setCity(cityEntity);
        Route routeEntity = routeRepository.save(route);

        ReviewCreationDto dto = new ReviewCreationDto("Тест", routeEntity.getId());
        reviewService.addReview(dto);

        List<Review> reviews = reviewRepository.findAll();
        assertThat(reviews).hasSize(1);
    }

}
