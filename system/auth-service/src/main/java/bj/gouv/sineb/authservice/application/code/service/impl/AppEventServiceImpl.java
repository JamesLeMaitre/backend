package bj.gouv.sineb.authservice.application.code.service.impl;

import bj.gouv.sineb.authservice.application.code.entity.AppEvent;
import bj.gouv.sineb.authservice.application.code.mapper.AppEventMapper;
import bj.gouv.sineb.authservice.application.code.dto.request.AppEventRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.AppEventResponse;
import bj.gouv.sineb.authservice.application.code.repository.AppEventRepository;
import bj.gouv.sineb.authservice.application.code.service.AppEventService;
import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class AppEventServiceImpl implements AppEventService {

    private final AppEventRepository repository;
    private final AppEventMapper appEventMapper;

    public AppEventServiceImpl(AppEventRepository repository, AppEventMapper appEventMapper) {
        this.repository = repository;
        this.appEventMapper = appEventMapper;
    }


    @Override
    @Transactional
    public AppEventResponse save(AppEventRequest request) {
        log.info("Saving AppEvent with payload: "+request.toString());
        AppEvent appEvent = appEventMapper.toEntity(request);
        AppEventResponse appEventResponse = appEventMapper.toDto(repository.save(appEvent));
        log.info("AppEvent is created with id: {}", appEvent.getId());
        log.info("Returning AppEventResponse with trackingId: {}", appEventResponse.getTrackingId());
        return appEventResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public AppEventResponse getOne(UUID id) {
        log.info("Searching for AppEvent with id: "+id);
        AppEvent appEvent = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("AppEvent with id: "+id+" not found!"));
        AppEventResponse appEventResponse = appEventMapper.toDto(appEvent);
        log.info("AppEvent is found with id: {}", appEvent.getId());
        log.info("Returning AppEventResponse with trackingId: {}", appEventResponse.getTrackingId());
        return appEventResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public AppEventResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for AppEvent with trackingId: "+trackingId);
        AppEvent appEvent = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("AppEvent with trackingId: "+trackingId+" not found!"));
        AppEventResponse appEventResponse = appEventMapper.toDto(appEvent);
        log.info("AppEvent is found with trackingId: {}", appEvent.getTrackingId());
        log.info("Returning AppEventResponse with trackingId: {}", appEventResponse.getTrackingId());
        return appEventResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppEventResponse> getAll(int page, int size, boolean deleted) {
        log.info("Searching for AppEvent list with (page, size, deleted) : "+"("+page+", "+size+", "+deleted+")");
        Page<AppEvent> appEventPage = repository.findAllByDeleted(deleted, PageRequest.of(page, size));
        Page<AppEventResponse> appEventPageResponse = appEventPage.map(appEventMapper::toDto);
        log.info("AppEvent list found with size: {}", appEventPage.getTotalElements());
        log.info("Returning AppEventResponse list with size: {}", appEventPage.getTotalElements());
        return appEventPageResponse;
    }

    @Override
    @Transactional
    public AppEventResponse updateOne(AppEventRequest request) {
        log.info("Updating AppEvent with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("AppEvent trackingId should not be null !");

        AppEvent appEvent = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("AppEvent with id: "+request.getTrackingId()+" not found!"));
        appEvent.update(request);
        AppEventResponse appEventResponse = appEventMapper.toDto(repository.save(appEvent));
        log.info("AppEvent is updated with id: {}", appEvent.getId());
        log.info("Returning AppEventResponse with trackingId: {}", appEventResponse.getTrackingId());
        return appEventResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting AppEvent with trackingId:: "+trackingId);
        AppEvent appEvent = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("AppEvent with id: "+trackingId+" not found!"));
        appEvent.delete();
        repository.save(appEvent);
        log.info("AppEvent is deleted with id: {}", appEvent.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countByDeletedFalse();
        log.info("AppEvent total count : {}", count);
        return count;
    }
}
