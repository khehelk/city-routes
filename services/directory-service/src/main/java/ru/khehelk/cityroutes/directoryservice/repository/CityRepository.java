package ru.khehelk.cityroutes.directoryservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khehelk.cityroutes.domain.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    Page<City> findByNameContaining(String name, Pageable pageable);

    List<City> findAllByRegionCode(Integer regionCode);

}