package ru.khehelk.cityroutes.directoryservice.web.api;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.khehelk.cityroutes.domain.dto.RouteInfoDto;

@Tag(name = "Управление маршрутами")
@RequestMapping("/api/v1/routes")
public interface RouteApi {

    @Operation(summary = "Получить список маршрутов в городе")
    @GetMapping
    ResponseEntity<Page<RouteInfoDto>> searchRoutesPage(@RequestParam("city_id") Long cityId,
                                                    Pageable pageable);

    @Operation(summary = "Получить всю информацию о маршруте")
    @GetMapping("{id}")
    ResponseEntity<RouteInfoDto> findRouteInfo(@PathVariable("id") Long id);

    @Operation(summary = "Добавить в избранное")
    @PostMapping("/add-in-favourite/{id}")
    ResponseEntity<String> addInFavourite(@PathVariable("id") Long id);

    @Operation(summary = "Убрать из избранных")
    @PostMapping("/remove-from-favourite/{id}")
    ResponseEntity<String> removeFromFavourite(@PathVariable("id") Long id);

    @Operation(summary = "Получить избранные")
    @GetMapping("/favourites")
    ResponseEntity<List<RouteInfoDto>> getFavourites();

}
