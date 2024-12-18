package ru.khehelk.cityroutes.reviewservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khehelk.cityroutes.domain.model.City;

public interface CityRepository extends JpaRepository<City, Long> {
}
