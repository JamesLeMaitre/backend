package bj.gouv.sineb.authservice.application.code.service.impl;

import bj.gouv.sineb.authservice.application.code.entity.Scope;
import bj.gouv.sineb.authservice.application.code.dto.request.ScopeRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.ScopeResponse;
import bj.gouv.sineb.authservice.application.code.mapper.ScopeMapper;
import bj.gouv.sineb.authservice.application.code.repository.ScopeRepository;
import bj.gouv.sineb.authservice.application.code.service.ScopeService;
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
public class ScopeServiceImpl implements ScopeService {


    private final  ScopeRepository repository;
    private final ScopeMapper scopeMapper;

    public ScopeServiceImpl(ScopeRepository repository, ScopeMapper scopeMapper) {
        this.repository = repository;
        this.scopeMapper = scopeMapper;
    }


    @Override
    @Transactional
    public ScopeResponse save(ScopeRequest request) {
        log.info("Saving Scope with payload: "+request.toString());
        Scope scope = scopeMapper.toEntity(request);
        ScopeResponse scopeResponse = scopeMapper.toDto(repository.save(scope));
        log.info("Scope is created with id: {}", scope.getId());
        log.info("Returning ScopeResponse with trackingId: {}", scopeResponse.getTrackingId());
        return scopeResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public ScopeResponse getOne(UUID id) {
        log.info("Searching for Scope with id: "+id);
        Scope scope = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("Scope with id: "+id+" not found!"));
        ScopeResponse scopeResponse = scopeMapper.toDto(scope);
        log.info("Scope is found with id: {}", scope.getId());
        log.info("Returning ScopeResponse with trackingId: {}", scopeResponse.getTrackingId());
        return scopeResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public ScopeResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for Scope with trackingId: "+trackingId);
        Scope scope = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Scope with trackingId: "+trackingId+" not found!"));
        ScopeResponse scopeResponse = scopeMapper.toDto(scope);
        log.info("Scope is found with trackingId: {}", scope.getTrackingId());
        log.info("Returning ScopeResponse with trackingId: {}", scopeResponse.getTrackingId());
        return scopeResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ScopeResponse> getAll(int page, int size, boolean deleted) {
        log.info("Searching for Scope list with (page, size, deleted) : "+"("+page+", "+size+", "+deleted+")");
        Page<Scope> scopePage = repository.findAllByDeleted(deleted, PageRequest.of(page, size));
        Page<ScopeResponse> scopePageResponse = scopePage.map(scopeMapper::toDto);
        log.info("Scope list found with size: {}", scopePage.getTotalElements());
        log.info("Returning ScopeResponse list with size: {}", scopePage.getTotalElements());
        return scopePageResponse;
    }

    @Override
    @Transactional
    public ScopeResponse updateOne(ScopeRequest request) {
        log.info("Updating Scope with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("Scope trackingId should not be null !");

        Scope scope = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Scope with id: "+request.getTrackingId()+" not found!"));
        scope.update(request);
        ScopeResponse scopeResponse = scopeMapper.toDto(repository.save(scope));
        log.info("Scope is updated with id: {}", scope.getId());
        log.info("Returning ScopeResponse with trackingId: {}", scopeResponse.getTrackingId());
        return scopeResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting Scope with trackingId:: "+trackingId);
        Scope scope = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Scope with id: "+trackingId+" not found!"));
        scope.delete();
        repository.save(scope);
        log.info("Scope is deleted with id: {}", scope.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countByDeletedFalse();
        log.info("Scope total count : {}", count);
        return count;
    }
}
