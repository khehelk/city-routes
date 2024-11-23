package ru.khehelk.cityroutes.adminservice.web.rest;

import java.net.URI;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.khehelk.cityroutes.adminservice.service.RouteService;
import ru.khehelk.cityroutes.adminservice.service.dto.RouteCreateDto;
import ru.khehelk.cityroutes.adminservice.service.dto.RouteDto;
import ru.khehelk.cityroutes.adminservice.service.dto.RouteInfoDto;
import ru.khehelk.cityroutes.adminservice.service.dto.RouteUpdateDto;
import ru.khehelk.cityroutes.adminservice.service.dto.RouteUpdateStopsDto;
import ru.khehelk.cityroutes.adminservice.web.api.RouteApi;

@RestController
@RequiredArgsConstructor
public class RouteContoller implements RouteApi {

    private final RouteService routeService;

    @Override
    public ResponseEntity<String> createAndSaveRoute(RouteCreateDto route) {
        routeService.createAndSaveRoute(route);
        return ResponseEntity.created(URI.create("/api/v1/routes")).build();
    }

    @Override
    public ResponseEntity<String> updateRoute(Long id, RouteUpdateDto route) {
        routeService.updateRoute(id, route);
        return ResponseEntity.ok("Запись успешно обновлена");
    }

    @Override
    public ResponseEntity<String> updateRouteStops(Long id, RouteUpdateStopsDto route) {
        routeService.updateRouteStops(id, route);
        return ResponseEntity.ok("Запись успешно обновлена");
    }

    @Override
    public ResponseEntity<String> deleteRoute(Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.ok("Запись успешно удалена");
    }

    @Override
    public ResponseEntity<Page<RouteDto>> searchRoutesPage(Integer cityCode,
                                                           Integer number,
                                                           Pageable pageable) {
        if (cityCode != null && number != null) {
            return ResponseEntity.ok(routeService.findAllBy(cityCode, number, pageable));
        }
        return ResponseEntity.ok(routeService.findAllBy(pageable));
    }

    @Override
    public ResponseEntity<RouteInfoDto> findRouteInfo(Long id) {
        return ResponseEntity.ok(routeService.findById(id));
    }

}
