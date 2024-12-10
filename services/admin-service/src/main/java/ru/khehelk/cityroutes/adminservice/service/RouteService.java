package ru.khehelk.cityroutes.adminservice.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khehelk.cityroutes.domain.mapper.RouteSimpleMapper;
import ru.khehelk.cityroutes.domain.model.Route;
import ru.khehelk.cityroutes.domain.model.RouteStops;
import ru.khehelk.cityroutes.domain.model.RouteStopsId;
import ru.khehelk.cityroutes.domain.model.Stop;
import ru.khehelk.cityroutes.businesslogic.repository.RouteRepository;
import ru.khehelk.cityroutes.adminservice.repository.RouteStopsRepository;
import ru.khehelk.cityroutes.domain.dto.RouteCreateDto;
import ru.khehelk.cityroutes.domain.dto.RouteDto;
import ru.khehelk.cityroutes.domain.dto.RouteInfoDto;
import ru.khehelk.cityroutes.domain.dto.RouteUpdateDto;
import ru.khehelk.cityroutes.domain.dto.RouteUpdateStopsDto;
import ru.khehelk.cityroutes.adminservice.service.mapper.RouteMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;

    private final RouteMapper routeMapper;

    private final RouteStopsRepository routeStopsRepository;

    private final StopService stopService;

    private final RouteSimpleMapper routeSimpleMapper;

    @Transactional
    public void createAndSaveRoute(RouteCreateDto route) {
        Route routeEntity = routeRepository.save(routeMapper.toEntity(route));
        routeStopsRepository.saveAll(getRouteStops(route, routeEntity));
    }

    private Iterable<RouteStops> getRouteStops(RouteCreateDto route,
                                               Route routeEntity) {
        return route.stops().entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(stop -> createRouteStops(stop, routeEntity)).toList();
    }

    private RouteStops createRouteStops(Map.Entry<Integer, Long> stop,
                                        Route routeEntity) {
        RouteStopsId id = new RouteStopsId();
        RouteStops routeStops = new RouteStops();
        id.setRouteId(routeEntity.getId());
        id.setStopId(stop.getValue());
        routeStops.setId(id);
        routeStops.setStopOrder(stop.getKey());
        routeStops.setRoute(routeEntity);
        routeStops.setStop(stopService.findById(stop.getValue()));
        return routeStops;
    }

    @Transactional
    public void updateRoute(Long id,
                            RouteUpdateDto route) {
        Route routeEntity = routeRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);
        routeStopsRepository.deleteAll(routeEntity.getStops());
        routeEntity.setFrequencyRangeStart(route.frequencyRangeStart());
        routeEntity.setFrequencyRangeEnd(route.frequencyRangeEnd());
        routeEntity.setStops(route.stops().entrySet().stream()
            .map(stop -> createRouteStops(stop, routeEntity)).collect(Collectors.toList()));
        routeStopsRepository.saveAll(routeEntity.getStops());
        routeRepository.save(routeEntity);
    }

    @Transactional
    public void updateRouteStops(Long id,
                                 RouteUpdateStopsDto routeStops) {
        Route routeEntity = routeRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);
        List<Stop> stops = stopService.findAllByIds(routeStops.stopIds().values().stream().toList());
        routeStopsRepository.deleteAllById_routeId(id);
        List<RouteStops> routeStopsList = routeStops.stopIds().entrySet().stream()
                .map(stop -> {
                    var routeStopsId = new RouteStopsId(id, stop.getValue());
                    var routeStopsEntity = new RouteStops();
                    routeStopsEntity.setId(routeStopsId);
                    routeStopsEntity.setStopOrder(stop.getKey());
                    routeStopsEntity.setRoute(routeEntity);
                    routeStopsEntity.setStop(stops.stream()
                        .filter(stopEntity -> stopEntity.getId().equals(stop.getValue()))
                        .findFirst().get());
                    return routeStopsEntity;
                }).toList();
        var entities = routeStopsRepository.saveAll(routeStopsList);
        routeEntity.setStops(entities);
        routeRepository.save(routeEntity);
    }

    @Transactional
    public void deleteRoute(Long id) {
        routeStopsRepository.deleteAllById_routeId(id);
        routeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<RouteDto> findAllBy(Long cityId,
                                    Integer number,
                                    Pageable pageable) {
        Page<Route> routePage = routeRepository.findByCity_idAndNumber(cityId, number, pageable);
        return new PageImpl<>(
            routePage.getContent().stream().map(routeSimpleMapper::toDto).toList(),
            routePage.getPageable(),
            routePage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<RouteDto> findAllBy(Pageable pageable) {
        Page<Route> routePage = routeRepository.findAll(pageable);
        return new PageImpl<>(
            routePage.getContent().stream().map(routeSimpleMapper::toDto).toList(),
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
