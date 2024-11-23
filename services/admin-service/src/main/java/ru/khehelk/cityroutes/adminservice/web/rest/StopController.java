package ru.khehelk.cityroutes.adminservice.web.rest;

import java.net.URI;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.khehelk.cityroutes.adminservice.service.StopService;
import ru.khehelk.cityroutes.adminservice.service.dto.StopDto;
import ru.khehelk.cityroutes.adminservice.service.dto.StopUpdateDto;
import ru.khehelk.cityroutes.adminservice.service.dto.CityDto;
import ru.khehelk.cityroutes.adminservice.service.dto.StopCreateDto;
import ru.khehelk.cityroutes.adminservice.web.api.StopApi;

@RestController
@RequiredArgsConstructor
public class StopController implements StopApi {

    private final StopService stopService;

    @Override
    public ResponseEntity<String> createAndSaveStop(StopCreateDto stop) {
        stopService.createAndSaveStop(stop);
        return ResponseEntity.created(URI.create("/api/v1/stops")).build();
    }

    @Override
    public ResponseEntity<String> updateStop(Long id,
                                             StopUpdateDto stop) {
        stopService.updateStop(id, stop);
        return ResponseEntity.ok("Запись успешно обновлена");
    }

    @Override
    public ResponseEntity<String> deleteStop(Long id) {
        stopService.deleteStop(id);
        return ResponseEntity.ok("Запись успешно удалена");
    }

    @Override
    public ResponseEntity<Page<StopDto>> searchStopsPage(Integer cityCode,
                                                         String stopName,
                                                         Pageable pageable) {
        if (cityCode != null && stopName != null && !stopName.isEmpty()) {
            return ResponseEntity.ok(stopService.findAllBy(cityCode, stopName, pageable));
        }
        return ResponseEntity.ok(stopService.findAllBy(pageable));
    }

}
