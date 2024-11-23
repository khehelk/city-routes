package ru.khehelk.cityroutes.adminservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khehelk.cityroutes.adminservice.domain.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    Page<Route> findByCity_codeAndNumber(Integer cityCode, Integer number, Pageable pageable);

}