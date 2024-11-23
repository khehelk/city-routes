package ru.khehelk.cityroutes.adminservice.web.rest;

import java.net.URI;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.khehelk.cityroutes.adminservice.service.CityService;
import ru.khehelk.cityroutes.adminservice.service.dto.CityCreateDto;
import ru.khehelk.cityroutes.adminservice.service.dto.CityDto;
import ru.khehelk.cityroutes.adminservice.service.dto.CityUpdateDto;
import ru.khehelk.cityroutes.adminservice.web.api.CityApi;

@RestController
@RequiredArgsConstructor
public class CityController implements CityApi {

    private final CityService cityService;

    @Override
    public ResponseEntity<String> createAndSaveCity(CityCreateDto city) {
        cityService.createAndSaveCity(city);
        return ResponseEntity.created(URI.create("/api/v1/cities")).build();
    }

    @Override
    public ResponseEntity<String> updateCity(Integer code,
                                             CityUpdateDto city) {
        cityService.updateCity(code, city);
        return ResponseEntity.ok("Запись успешно обновлена");
    }

    @Override
    public ResponseEntity<String> deleteCity(Integer code) {
        cityService.deleteCity(code);
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

}
