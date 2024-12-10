package ru.khehelk.cityroutes.businesslogic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khehelk.cityroutes.domain.model.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    Page<Route> findByCity_idAndNumber(Long id, Integer number, Pageable pageable);

    Page<Route> findByCity_idOrderByNumber(Long cityId, Pageable pageable);

}