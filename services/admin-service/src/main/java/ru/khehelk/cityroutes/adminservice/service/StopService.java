package ru.khehelk.cityroutes.adminservice.service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khehelk.cityroutes.domain.mapper.StopSimpleMapper;
import ru.khehelk.cityroutes.domain.model.Stop;
import ru.khehelk.cityroutes.businesslogic.repository.StopRepository;
import ru.khehelk.cityroutes.domain.dto.StopCreateDto;
import ru.khehelk.cityroutes.domain.dto.StopDto;
import ru.khehelk.cityroutes.domain.dto.StopUpdateDto;
import ru.khehelk.cityroutes.adminservice.service.mapper.StopMapper;

@Service
@RequiredArgsConstructor
public class StopService {

    private final StopRepository stopRepository;

    private final StopMapper stopMapper;

    private final StopSimpleMapper stopSimpleMapper;

    @Transactional
    public void createAndSaveStop(StopCreateDto stop) {
        if (stopRepository.existsByCity_IdAndName(stop.cityId(), stop.name())) {
            throw new IllegalArgumentException("Такая остановка же существует");
        }
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
    public List<StopDto> findAllBy(Long cityId) {
        List<Stop> stops = stopRepository.findAllByCity_id(cityId);
        return stops.stream().map(stopSimpleMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Page<StopDto> findAllBy(Pageable pageable) {
        Page<Stop> stopPage = stopRepository.findAll(pageable);
        return new PageImpl<>(
            stopPage.getContent().stream().map(stopSimpleMapper::toDto).toList(),
            stopPage.getPageable(),
            stopPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<Stop> findAllByIds(List<Long> stopIds) {
        return stopRepository.findAllById(stopIds);
    }

    @Transactional(readOnly = true)
    public Stop findById(Long value) {
        return stopRepository.findById(value)
            .orElseThrow(() -> new EntityNotFoundException("Остановка с таким id не найдена"));
    }

    public List<Stop> findAll() {
        return stopRepository.findAll();
    }
}
