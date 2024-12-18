package ru.khehelk.cityroutes.adminservice.web.rest;

import java.net.URI;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.khehelk.cityroutes.adminservice.service.StopService;
import ru.khehelk.cityroutes.adminservice.web.api.StopApi;
import ru.khehelk.cityroutes.domain.dto.StopCreateDto;
import ru.khehelk.cityroutes.domain.dto.StopUpdateDto;

@RestController
@RequiredArgsConstructor
public class StopController implements StopApi {

    private final StopService stopService;

    @Override
    public ResponseEntity<String> createAndSaveStop(StopCreateDto stop) {
        stopService.createAndSaveStop(stop);
        return ResponseEntity.created(URI.create("/api/v1/stops"))
            .body("Остановка успешно сохранена");
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
    public ResponseEntity<?> searchStopsPage(Long cityId) {
        if (cityId == null) {
            return ResponseEntity.badRequest()
                .body("Город не указан");
        }
        return ResponseEntity.ok(stopService.findAllBy(cityId));
    }

}
