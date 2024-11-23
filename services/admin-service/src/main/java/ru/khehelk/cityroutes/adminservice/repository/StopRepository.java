package ru.khehelk.cityroutes.adminservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khehelk.cityroutes.adminservice.domain.Stop;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {

    Page<Stop> findAllByCity_codeAndNameContaining(Integer cityCode, String upperCase, Pageable pageable);

}
