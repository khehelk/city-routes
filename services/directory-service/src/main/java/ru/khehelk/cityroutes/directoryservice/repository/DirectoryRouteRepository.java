package ru.khehelk.cityroutes.directoryservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khehelk.cityroutes.directoryservice.domain.RouteEntity;

@Repository
public interface DirectoryRouteRepository extends JpaRepository<RouteEntity, Long> {

    Page<RouteEntity> findByCity_idAndNumber(Long id, Integer number, Pageable pageable);

    Page<RouteEntity> findByCity_idOrderByNumber(Long cityId, Pageable pageable);

    List<RouteEntity> findAllByIdIn(List<Long> collect);
}