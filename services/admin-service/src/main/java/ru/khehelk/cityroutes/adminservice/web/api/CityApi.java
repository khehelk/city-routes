package ru.khehelk.cityroutes.adminservice.web.api;

import java.util.List;

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
import ru.khehelk.cityroutes.domain.dto.CityCreateDto;
import ru.khehelk.cityroutes.domain.dto.CityDto;
import ru.khehelk.cityroutes.domain.dto.CityUpdateDto;

@Tag(name = "Управление городами")
@RequestMapping("/api/v1/cities")
public interface CityApi {

    @Operation(summary = "Добавить город")
    @PostMapping
    ResponseEntity<String> createAndSaveCity(@RequestBody CityCreateDto city);

    @Operation(summary = "Обновить данные о городе")
    @PatchMapping("/{code}")
    ResponseEntity<String> updateCity(@PathVariable("code") Long code,
                                      @RequestBody CityUpdateDto city);

    @Operation(summary = "Удалить город")
    @DeleteMapping("/{code}")
    ResponseEntity<String> deleteCity(@PathVariable("code") Long code);

    @Operation(summary = "Получить список городов")
    @GetMapping
    ResponseEntity<Page<CityDto>> getCitiesPage(@RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                Pageable pageable);

    @Operation(summary = "Получить список городов по коду региона")
    @GetMapping("/in-region/{regionCode}")
    ResponseEntity<List<CityDto>> getCitiesByRegionCode(@PathVariable(name = "regionCode") Integer regionCode);

}
