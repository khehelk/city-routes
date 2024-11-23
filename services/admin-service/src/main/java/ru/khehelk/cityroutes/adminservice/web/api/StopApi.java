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
import ru.khehelk.cityroutes.adminservice.service.dto.CityCreateDto;
import ru.khehelk.cityroutes.adminservice.service.dto.CityDto;
import ru.khehelk.cityroutes.adminservice.service.dto.CityUpdateDto;
import ru.khehelk.cityroutes.adminservice.service.dto.StopCreateDto;
import ru.khehelk.cityroutes.adminservice.service.dto.StopDto;
import ru.khehelk.cityroutes.adminservice.service.dto.StopUpdateDto;

@Tag(name = "Управление остановками")
@RequestMapping("/api/v1/stops")
public interface StopApi {

    @Operation(summary = "Добавить остановку")
    @PostMapping
    ResponseEntity<String> createAndSaveStop(@RequestBody StopCreateDto stop);

    @Operation(summary = "Обновить данные об остановке")
    @PatchMapping("/{id}")
    ResponseEntity<String> updateStop(@PathVariable("id") Long id,
                                      @RequestBody StopUpdateDto stop);

    @Operation(summary = "Удалить остановку")
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteStop(@PathVariable("id") Long id);

    @Operation(summary = "Получить список остановок в городе")
    @GetMapping
    ResponseEntity<Page<StopDto>> searchStopsPage(@RequestParam("city_code") Integer cityCode,
                                                   @RequestParam(value = "stop_name", required = false) String stopName,
                                                   Pageable pageable);

}
