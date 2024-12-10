package ru.khehelk.cityroutes.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.khehelk.cityroutes.domain.model.Stop;
import ru.khehelk.cityroutes.domain.dto.StopDto;

@Service
@RequiredArgsConstructor
public class StopSimpleMapper {

    public StopDto toDto(Stop stop) {
        return new StopDto(
            stop.getId(),
            stop.getCity().getId(),
            stop.getName()
        );
    }

}
