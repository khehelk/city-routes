package ru.khehelk.cityroutes.adminservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.khehelk.cityroutes.adminservice.domain.RouteStops;
import ru.khehelk.cityroutes.adminservice.domain.RouteStopsId;

@Repository
public interface RouteStopsRepository extends JpaRepository<RouteStops, RouteStopsId> {

    void deleteAllById_routeId(Long id);

}