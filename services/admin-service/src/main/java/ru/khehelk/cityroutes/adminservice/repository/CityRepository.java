package ru.khehelk.cityroutes.adminservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.khehelk.cityroutes.adminservice.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    Page<City> findByNameContaining(String name, Pageable pageable);

}