package ru.khehelk.cityroutes.adminservice.service;

import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khehelk.cityroutes.adminservice.domain.City;
import ru.khehelk.cityroutes.adminservice.domain.Stop;
import ru.khehelk.cityroutes.adminservice.repository.StopRepository;
import ru.khehelk.cityroutes.adminservice.service.dto.StopCreateDto;
import ru.khehelk.cityroutes.adminservice.service.dto.StopDto;
import ru.khehelk.cityroutes.adminservice.service.dto.StopUpdateDto;
import ru.khehelk.cityroutes.adminservice.service.mapper.StopMapper;

@Service
@RequiredArgsConstructor
public class StopService {

    private final StopRepository stopRepository;

    private final StopMapper stopMapper;

    @Transactional
    public void createAndSaveStop(StopCreateDto stop) {
        stopRepository.save(stopMapper.toEntity(stop));
    }

    @Transactional
    public void updateStop(Long id, StopUpdateDto stop) {
        Stop stopEntity = stopRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);
        stopEntity.setName(stop.name());
        stopRepository.save(stopEntity);
    }

    @Transactional
    public void deleteStop(Long id) {
        stopRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<StopDto> findAllBy(Integer cityCode,
                                   String stopName,
                                   Pageable pageable) {
        Page<Stop> stopPage = stopRepository.findAllByCity_codeAndNameContaining(cityCode, stopName.toUpperCase(), pageable);
        return new PageImpl<>(
            stopPage.getContent().stream().map(stopMapper::toDto).toList(),
            stopPage.getPageable(),
            stopPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<StopDto> findAllBy(Pageable pageable) {
        Page<Stop> stopPage = stopRepository.findAll(pageable);
        return new PageImpl<>(
            stopPage.getContent().stream().map(stopMapper::toDto).toList(),
            stopPage.getPageable(),
            stopPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<Stop> findAllByIds(List<Long> stopIds) {
        return stopRepository.findAllById(stopIds);
    }

    @Transactional(readOnly = true)
    public Stop findById(Long value) {
        return stopRepository.findById(value).orElseThrow(EntityNotFoundException::new);
    }
}
