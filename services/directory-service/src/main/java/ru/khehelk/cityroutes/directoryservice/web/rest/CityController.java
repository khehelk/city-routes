package ru.khehelk.cityroutes.directoryservice.web.rest;

import java.net.URI;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.khehelk.cityroutes.directoryservice.service.CityService;
import ru.khehelk.cityroutes.directoryservice.web.api.CityApi;
import ru.khehelk.cityroutes.domain.dto.CityDto;

@RestController
@RequiredArgsConstructor
public class CityController implements CityApi {

    private final CityService cityService;

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
