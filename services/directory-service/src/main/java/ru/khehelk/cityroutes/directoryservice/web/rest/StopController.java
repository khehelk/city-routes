package ru.khehelk.cityroutes.directoryservice.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.khehelk.cityroutes.directoryservice.service.StopService;
import ru.khehelk.cityroutes.directoryservice.web.api.StopApi;
import ru.khehelk.cityroutes.domain.dto.StopDto;

@RestController
@RequiredArgsConstructor
public class StopController implements StopApi {

    private final StopService stopService;

    @Override
    public ResponseEntity<Page<StopDto>> searchStopsPage(Long cityId,
                                                         String cityName,
                                                         Pageable pageable) {
        if (cityId != null && cityName != null && !cityName.isEmpty()) {
            return ResponseEntity.ok(stopService.findAllBy(cityId, cityName, pageable));
        }
        return ResponseEntity.ok(stopService.findAllBy(pageable));
    }

}
