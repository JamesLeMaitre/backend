package bj.gouv.sineb.authservice.application.code.service.impl;

import bj.gouv.sineb.authservice.application.code.entity.SystemVariable;
import bj.gouv.sineb.authservice.application.code.mapper.SystemVariableMapper;
import bj.gouv.sineb.authservice.application.code.dto.request.SystemVariableRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.SystemVariableResponse;
import bj.gouv.sineb.authservice.application.code.repository.SystemVariableRepository;
import bj.gouv.sineb.authservice.application.code.service.SystemVariableService;
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
public class SystemVariableServiceImpl implements SystemVariableService {

    private final  SystemVariableRepository repository;
    private final SystemVariableMapper systemVariableMapper;

    public SystemVariableServiceImpl(SystemVariableRepository repository, SystemVariableMapper systemVariableMapper) {
        this.repository = repository;
        this.systemVariableMapper = systemVariableMapper;
    }


    @Override
    @Transactional
    public SystemVariableResponse save(SystemVariableRequest request) {
        log.info("Saving SystemVariable with payload: "+request.toString());
        SystemVariable systemVariable = systemVariableMapper.toEntity(request);
        SystemVariableResponse systemVariableResponse = systemVariableMapper.toDto(repository.save(systemVariable));
        log.info("SystemVariable is created with id: {}", systemVariable.getId());
        log.info("Returning SystemVariableResponse with trackingId: {}", systemVariableResponse.getTrackingId());
        return systemVariableResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public SystemVariableResponse getOne(UUID id) {
        log.info("Searching for SystemVariable with id: "+id);
        SystemVariable systemVariable = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("SystemVariable with id: "+id+" not found!"));
        SystemVariableResponse systemVariableResponse = systemVariableMapper.toDto(systemVariable);
        log.info("SystemVariable is found with id: {}", systemVariable.getId());
        log.info("Returning SystemVariableResponse with trackingId: {}", systemVariableResponse.getTrackingId());
        return systemVariableResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public SystemVariableResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for SystemVariable with trackingId: "+trackingId);
        SystemVariable systemVariable = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("SystemVariable with trackingId: "+trackingId+" not found!"));
        SystemVariableResponse systemVariableResponse = systemVariableMapper.toDto(systemVariable);
        log.info("SystemVariable is found with trackingId: {}", systemVariable.getTrackingId());
        log.info("Returning SystemVariableResponse with trackingId: {}", systemVariableResponse.getTrackingId());
        return systemVariableResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SystemVariableResponse> getAll(int page, int size, boolean deleted) {
        log.info("Searching for SystemVariable list with (page, size, deleted) : "+"("+page+", "+size+", "+deleted+")");
        Page<SystemVariable> systemVariablePage = repository.findAllByDeleted(deleted, PageRequest.of(page, size));
        Page<SystemVariableResponse> systemVariablePageResponse = systemVariablePage.map(systemVariableMapper::toDto);
        log.info("SystemVariable list found with size: {}", systemVariablePage.getTotalElements());
        log.info("Returning SystemVariableResponse list with size: {}", systemVariablePage.getTotalElements());
        return systemVariablePageResponse;
    }

    @Override
    @Transactional
    public SystemVariableResponse updateOne(SystemVariableRequest request) {
        log.info("Updating SystemVariable with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("SystemVariable trackingId should not be null !");

        SystemVariable systemVariable = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("SystemVariable with id: "+request.getTrackingId()+" not found!"));
        systemVariable.update(request);
        SystemVariableResponse systemVariableResponse = systemVariableMapper.toDto(repository.save(systemVariable));
        log.info("SystemVariable is updated with id: {}", systemVariable.getId());
        log.info("Returning SystemVariableResponse with trackingId: {}", systemVariableResponse.getTrackingId());
        return systemVariableResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting SystemVariable with trackingId:: "+trackingId);
        SystemVariable systemVariable = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("SystemVariable with id: "+trackingId+" not found!"));
        systemVariable.delete();
        repository.save(systemVariable);
        log.info("SystemVariable is deleted with id: {}", systemVariable.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countByDeletedFalse();
        log.info("SystemVariable total count : {}", count);
        return count;
    }

}
