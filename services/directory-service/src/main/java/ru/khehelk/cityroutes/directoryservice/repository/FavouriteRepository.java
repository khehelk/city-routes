package ru.khehelk.cityroutes.directoryservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khehelk.cityroutes.directoryservice.domain.Favourite;
import ru.khehelk.cityroutes.directoryservice.domain.FavouriteId;

public interface FavouriteRepository extends JpaRepository<Favourite, FavouriteId> {
    List<Favourite> findAllByUser_Username(String userUsername);
}