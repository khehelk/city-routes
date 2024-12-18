package ru.khehelk.cityroutes.adminservice.web.rest;

import java.net.URI;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.khehelk.cityroutes.adminservice.service.CityService;
import ru.khehelk.cityroutes.adminservice.web.api.CityApi;
import ru.khehelk.cityroutes.domain.dto.CityCreateDto;
import ru.khehelk.cityroutes.domain.dto.CityDto;
import ru.khehelk.cityroutes.domain.dto.CityUpdateDto;

@RestController
@RequiredArgsConstructor
public class CityController implements CityApi {

    private final CityService cityService;

    @Override
    public ResponseEntity<String> createAndSaveCity(CityCreateDto city) {
        cityService.createAndSaveCity(city);
        return ResponseEntity.created(URI.create("/api/v1/cities"))
            .body("Город успешно сохранен");
    }

    @Override
    public ResponseEntity<String> updateCity(Long id,
                                             CityUpdateDto city) {
        cityService.updateCity(id, city);
        return ResponseEntity.ok("Запись успешно обновлена");
    }

    @Override
    public ResponseEntity<String> deleteCity(Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.ok("Запись успешно удалена");
    }

    @Override
    public ResponseEntity<Page<CityDto>> getCitiesPage(String searchTerm,
                                                       Pageable pageable) {
        if (searchTerm != null && !searchTerm.isBlank()) {
            return ResponseEntity.ok(cityService.findAllBy(searchTerm.toUpperCase(), pageable));
        }
        return ResponseEntity.ok(cityService.findAllBy(pageable));
    }

    @Override
    public ResponseEntity<List<CityDto>> getCitiesByRegionCode(Integer regionCode) {
        if (regionCode != null) {
            return ResponseEntity.ok(cityService.findAllBy(regionCode));
        }
        return ResponseEntity.ok(List.of());
    }

}
