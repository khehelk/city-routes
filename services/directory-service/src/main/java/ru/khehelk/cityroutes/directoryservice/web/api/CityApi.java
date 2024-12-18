package ru.khehelk.cityroutes.directoryservice.web.api;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.khehelk.cityroutes.domain.dto.CityDto;

@Tag(name = "Управление городами")
@RequestMapping("/api/v1/cities")
public interface CityApi {

    @Operation(summary = "Поиск городов")
    @GetMapping
    ResponseEntity<Page<CityDto>> getCitiesPage(@RequestParam(name = "search_term", required = false) String searchTerm,
                                                Pageable pageable);

    @Operation(summary = "Получить список городов по коду региона")
    @GetMapping("/in-region/{regionCode}")
    ResponseEntity<List<CityDto>> getCitiesByRegionCode(@PathVariable(name = "regionCode") Integer regionCode);

}
