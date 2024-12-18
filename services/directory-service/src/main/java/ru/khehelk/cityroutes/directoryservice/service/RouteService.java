package ru.khehelk.cityroutes.directoryservice.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khehelk.cityroutes.directoryservice.domain.Favourite;
import ru.khehelk.cityroutes.directoryservice.domain.FavouriteId;
import ru.khehelk.cityroutes.directoryservice.domain.RouteEntity;
import ru.khehelk.cityroutes.directoryservice.domain.User;
import ru.khehelk.cityroutes.directoryservice.repository.DirectoryRouteRepository;
import ru.khehelk.cityroutes.directoryservice.repository.FavouriteRepository;
import ru.khehelk.cityroutes.directoryservice.repository.UserRepository;
import ru.khehelk.cityroutes.directoryservice.service.mapper.RouteMapper;
import ru.khehelk.cityroutes.domain.dto.RouteInfoDto;
import ru.khehelk.cityroutes.domain.mapper.RouteSimpleMapper;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final DirectoryRouteRepository routeRepository;

    private final RouteMapper routeMapper;

    private final UserRepository userRepository;

    private final FavouriteRepository favouriteRepository;

    @Transactional(readOnly = true)
    public Page<RouteInfoDto> findAllBy(Long cityId,
                                        Pageable pageable) {
        Page<RouteEntity> routePage = routeRepository.findByCity_idOrderByNumber(cityId, pageable);
        return new PageImpl<>(
            routePage.getContent().stream().map(routeMapper::toInfoDto).toList(),
            routePage.getPageable(),
            routePage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<RouteInfoDto> findAllBy(Pageable pageable) {
        Page<RouteEntity> routePage = routeRepository.findAll(pageable);
        return new PageImpl<>(
            routePage.getContent().stream().map(routeMapper::toInfoDto).toList(),
            routePage.getPageable(),
            routePage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public RouteInfoDto findById(Long id) {
        RouteEntity route = routeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Маршрут с таким id не найден"));
        return routeMapper.toInfoDto(route);
    }

    @Transactional
    public void addInFavourite(Long id) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userEntity = userRepository.findByUsername(user.getUsername());
        RouteEntity route = routeRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);
        if (route.getFavourites().size() == 10) {
            throw new IllegalArgumentException("Достигнуто максимальное количество избранных маршрутов");
        }
        FavouriteId favouriteId = new FavouriteId(userEntity.getId(), route.getId());
        Favourite favourite = new Favourite();
        favourite.setId(favouriteId);
        favourite.setUser(userEntity);
        favourite.setRoute(route);
        Favourite favouriteEntity = favouriteRepository.save(favourite);
        route.getFavourites().add(favouriteEntity);
        routeRepository.save(route);
    }

    @Transactional
    public void removeFromFavourite(Long id) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userEntity = userRepository.findByUsername(user.getUsername());
        RouteEntity route = routeRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);
        Favourite favourite = favouriteRepository.findById(new FavouriteId(userEntity.getId(), id))
            .orElseThrow(EntityNotFoundException::new);
        route.getFavourites().remove(favourite);
        routeRepository.save(route);
        favouriteRepository.delete(favourite);
    }

    @Transactional(readOnly = true)
    public List<RouteInfoDto> getFavourites() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Favourite> favourites = favouriteRepository.findAllByUser_Username(user.getUsername());
        List<RouteEntity> routes = routeRepository.findAllByIdIn(
            favourites.stream()
                .map(Favourite::getRoute)
                .map(RouteEntity::getId)
                .collect(Collectors.toList())
        );
        return routes.stream().map(routeMapper::toInfoDto).toList();
    }
}
