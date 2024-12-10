package ru.khehelk.cityroutes.adminservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khehelk.cityroutes.domain.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Page<City> findByNameContaining(String name, Pageable pageable);

    boolean existsByRegionCode(Integer regionCode);

    Optional<City> findByRegionCode(Integer regionCode);

    List<City> findAllByRegionCode(Integer regionCode);

}