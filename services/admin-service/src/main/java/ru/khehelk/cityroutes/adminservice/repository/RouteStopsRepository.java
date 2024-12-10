package ru.khehelk.cityroutes.adminservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khehelk.cityroutes.domain.model.RouteStops;
import ru.khehelk.cityroutes.domain.model.RouteStopsId;

@Repository
public interface RouteStopsRepository extends JpaRepository<RouteStops, RouteStopsId> {

    void deleteAllById_routeId(Long id);

}