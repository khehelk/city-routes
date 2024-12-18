package ru.khehelk.cityroutes.directoryservice.service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khehelk.cityroutes.businesslogic.repository.StopRepository;
import ru.khehelk.cityroutes.domain.dto.StopDto;
import ru.khehelk.cityroutes.domain.mapper.StopSimpleMapper;
import ru.khehelk.cityroutes.domain.model.Stop;

@Service
@RequiredArgsConstructor
public class StopService {

    private final StopRepository stopRepository;

    private final StopSimpleMapper stopSimpleMapper;

    @Transactional(readOnly = true)
    public Page<StopDto> findAllBy(Long cityId,
                                   String cityName,
                                   Pageable pageable) {
        Page<Stop> stopPage = stopRepository.findAllByCity_idAndCity_nameEquals(cityId, cityName.toUpperCase(), pageable);
        return new PageImpl<>(
            stopPage.getContent().stream().map(stopSimpleMapper::toDto).toList(),
            stopPage.getPageable(),
            stopPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<StopDto> findAllBy(Pageable pageable) {
        Page<Stop> stopPage = stopRepository.findAll(pageable);
        return new PageImpl<>(
            stopPage.getContent().stream().map(stopSimpleMapper::toDto).toList(),
            stopPage.getPageable(),
            stopPage.getTotalElements());
    }

}
