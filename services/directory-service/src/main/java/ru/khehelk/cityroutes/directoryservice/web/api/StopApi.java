package ru.khehelk.cityroutes.directoryservice.web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.khehelk.cityroutes.domain.dto.StopDto;

@Tag(name = "Управление остановками")
@RequestMapping("/api/v1/stops")
public interface StopApi {

    @Operation(summary = "Получить список остановок в городе")
    @GetMapping
    ResponseEntity<Page<StopDto>> searchStopsPage(@RequestParam("city_id") Long cityId,
                                                  @RequestParam(name = "city_name", required = false) String cityName,
                                                  Pageable pageable);

}
