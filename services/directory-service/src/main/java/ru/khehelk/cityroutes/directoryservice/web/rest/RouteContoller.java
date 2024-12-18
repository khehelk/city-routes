package ru.khehelk.cityroutes.directoryservice.web.rest;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.khehelk.cityroutes.directoryservice.service.RouteService;
import ru.khehelk.cityroutes.directoryservice.web.api.RouteApi;
import ru.khehelk.cityroutes.domain.dto.RouteInfoDto;

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

    @Override
    public ResponseEntity<String> addInFavourite(Long id) {
        routeService.addInFavourite(id);
        return ResponseEntity.ok("Успешно добавлено в избранные");
    }

    @Override
    public ResponseEntity<String> removeFromFavourite(Long id) {
        routeService.removeFromFavourite(id);
        return ResponseEntity.ok("Успешно удалено из избранных");
    }

    @Override
    public ResponseEntity<List<RouteInfoDto>> getFavourites() {
        return ResponseEntity.ok(routeService.getFavourites());
    }

}
