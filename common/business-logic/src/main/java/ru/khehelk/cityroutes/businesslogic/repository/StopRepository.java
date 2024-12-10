package ru.khehelk.cityroutes.businesslogic.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khehelk.cityroutes.domain.model.Stop;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {

    Page<Stop> findAllByCity_idAndCity_nameEquals(Long cityId, String upperCase, Pageable pageable);

    boolean existsByCity_IdAndName(Long aLong, String name);

    List<Stop> findAllByCity_id(Long cityId);

}
