package ru.khehelk.cityroutes.directoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khehelk.cityroutes.businesslogic.repository.RouteRepository;
import ru.khehelk.cityroutes.directoryservice.service.mapper.RouteMapper;
import ru.khehelk.cityroutes.domain.mapper.RouteSimpleMapper;
import ru.khehelk.cityroutes.domain.dto.RouteInfoDto;
import ru.khehelk.cityroutes.domain.model.Route;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;

    private final RouteSimpleMapper routeSimpleMapper;

    private final RouteMapper routeMapper;

    @Transactional(readOnly = true)
    public Page<RouteInfoDto> findAllBy(Long cityId,
                                        Pageable pageable) {
        Page<Route> routePage = routeRepository.findByCity_idOrderByNumber(cityId, pageable);
        return new PageImpl<>(
            routePage.getContent().stream().map(routeMapper::toInfoDto).toList(),
            routePage.getPageable(),
            routePage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<RouteInfoDto> findAllBy(Pageable pageable) {
        Page<Route> routePage = routeRepository.findAll(pageable);
        return new PageImpl<>(
            routePage.getContent().stream().map(routeMapper::toInfoDto).toList(),
            routePage.getPageable(),
            routePage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public RouteInfoDto findById(Long id) {
        Route route = routeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Маршрут с таким id не найден"));
        return routeMapper.toInfoDto(route);
    }

}
