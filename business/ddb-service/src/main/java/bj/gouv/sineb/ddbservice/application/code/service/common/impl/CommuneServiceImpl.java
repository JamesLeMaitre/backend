package bj.gouv.sineb.ddbservice.application.code.service.common.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.CommuneRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.CommuneResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Commune;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.CommuneMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.CommuneRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.CommuneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class CommuneServiceImpl implements CommuneService {
    private final CommuneRepository repository;
    private final CommuneMapper communeMapper;

    public CommuneServiceImpl(CommuneRepository repository, CommuneMapper communeMapper) {
        this.repository = repository;
        this.communeMapper = communeMapper;
    }


    @Override
    @Transactional
    public CommuneResponse save(CommuneRequest request) {
        log.info("Saving Commune with payload: "+request.toString());
        Commune commune = communeMapper.toEntity(request);
        commune.setDataStatus(DataStatus.CREATED);
        CommuneResponse communeResponse = communeMapper.toDto(repository.save(commune));
        log.info("Commune is created with id: {}", commune.getId());
        log.info("Returning CommuneResponse with trackingId: {}", communeResponse.getTrackingId());
        return communeResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public CommuneResponse getOne(UUID id) {
        log.info("Searching for Commune with id: "+id);
        Commune commune = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("Commune with id: "+id+" not found!"));
        CommuneResponse communeResponse = communeMapper.toDto(commune);
        log.info("Commune is found with id: {}", commune.getId());
        log.info("Returning CommuneResponse with trackingId: {}", communeResponse.getTrackingId());
        return communeResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public CommuneResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for Commune with trackingId: "+trackingId);
        Commune commune = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Commune with trackingId: "+trackingId+" not found!"));
        CommuneResponse communeResponse = communeMapper.toDto(commune);
        log.info("Commune is found with trackingId: {}", commune.getTrackingId());
        log.info("Returning CommuneResponse with trackingId: {}", communeResponse.getTrackingId());
        return communeResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommuneResponse> getAll(int page, int size) {
        log.info("Searching for Commune list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<Commune> communePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<CommuneResponse> communePageResponse = communePage.map(communeMapper::toDto);
        log.info("Commune list found with size: {}", communePage.getTotalElements());
        log.info("Returning CommuneResponse list with size: {}", communePage.getTotalElements());
        return communePageResponse;
    }

    @Override
    @Transactional
    public CommuneResponse updateOne(CommuneRequest request) {
        log.info("Updating Commune with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("Commune trackingId should not be null !");

        Commune commune = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Commune with id: "+request.getTrackingId()+" not found!"));
        commune.setDataStatus(DataStatus.UPDATED);
        CommuneResponse communeResponse = communeMapper.toDto(repository.save(commune));
        log.info("Commune is updated with id: {}", commune.getId());
        log.info("Returning CommuneResponse with trackingId: {}", communeResponse.getTrackingId());
        return communeResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting Commune with trackingId:: "+trackingId);
        Commune commune = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Commune with id: "+trackingId+" not found!"));
        commune.setDataStatus(DataStatus.DELETED);
        repository.save(commune);
        log.info("Commune is deleted with id: {}", commune.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("Commune total count : {}", count);
        return count;
    }
}
