package ru.khehelk.cityroutes.adminservice.web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.khehelk.cityroutes.domain.dto.RouteCreateDto;
import ru.khehelk.cityroutes.domain.dto.RouteDto;
import ru.khehelk.cityroutes.domain.dto.RouteInfoDto;
import ru.khehelk.cityroutes.domain.dto.RouteUpdateDto;
import ru.khehelk.cityroutes.domain.dto.RouteUpdateStopsDto;

@Tag(name = "Управление маршрутами")
@RequestMapping("/api/v1/routes")
public interface RouteApi {

    @Operation(summary = "Добавить маршрут")
    @PostMapping
    ResponseEntity<String> createAndSaveRoute(@RequestBody RouteCreateDto route);

    @Operation(summary = "Обновить данные о маршруте")
    @PatchMapping("/{id}")
    ResponseEntity<String> updateRoute(@PathVariable("id") Long id,
                                       @RequestBody RouteUpdateDto route);

    @Operation(summary = "Обновить остановки маршрута")
    @PatchMapping("/{id}/stops")
    ResponseEntity<String> updateRouteStops(@PathVariable("id") Long id,
                                            @RequestBody RouteUpdateStopsDto route);

    @Operation(summary = "Удалить маршрут")
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteRoute(@PathVariable("id") Long id);

    @Operation(summary = "Получить список маршрутов в городе")
    @GetMapping
    ResponseEntity<Page<RouteDto>> searchRoutesPage(@RequestParam("city_id") Long cityId,
                                                    @RequestParam(value = "route_number", required = false) Integer number,
                                                    Pageable pageable);

    @Operation(summary = "Получить всю информацию о маршруте")
    @GetMapping("/{id}")
    ResponseEntity<RouteInfoDto> findRouteInfo(@PathVariable("id") Long id);

}
