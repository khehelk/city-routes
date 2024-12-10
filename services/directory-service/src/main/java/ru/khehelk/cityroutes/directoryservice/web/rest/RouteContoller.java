package ru.khehelk.cityroutes.directoryservice.web.rest;

import java.net.URI;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.khehelk.cityroutes.directoryservice.service.RouteService;
import ru.khehelk.cityroutes.domain.dto.RouteCreateDto;
import ru.khehelk.cityroutes.domain.dto.RouteDto;
import ru.khehelk.cityroutes.domain.dto.RouteInfoDto;
import ru.khehelk.cityroutes.domain.dto.RouteUpdateDto;
import ru.khehelk.cityroutes.domain.dto.RouteUpdateStopsDto;
import ru.khehelk.cityroutes.directoryservice.web.api.RouteApi;

@RestController
@RequiredArgsConstructor
public class RouteContoller implements RouteApi {

    private final RouteService routeService;

    @Override
    public ResponseEntity<Page<RouteInfoDto>> searchRoutesPage(Long cityId,
                                                               Pageable pageable) {
        if (cityId != null) {
            return ResponseEntity.ok(routeService.findAllBy(cityId, pageable));
        }
        return ResponseEntity.ok(routeService.findAllBy(pageable));
    }

    @Override
    public ResponseEntity<RouteInfoDto> findRouteInfo(Long id) {
        return ResponseEntity.ok(routeService.findById(id));
    }

}
