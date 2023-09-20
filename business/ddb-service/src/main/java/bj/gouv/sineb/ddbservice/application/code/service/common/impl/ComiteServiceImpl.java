package bj.gouv.sineb.ddbservice.application.code.service.common.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.ComiteRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.ComiteResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Comite;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.ComiteMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.ComiteRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.ComiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class ComiteServiceImpl implements ComiteService {
    private final ComiteRepository repository;
    private final ComiteMapper comiteMapper;

    public ComiteServiceImpl(ComiteRepository repository, ComiteMapper comiteMapper) {
        this.repository = repository;
        this.comiteMapper = comiteMapper;
    }


    @Override
    @Transactional
    public ComiteResponse save(ComiteRequest request) {
        log.info("Saving Comite with payload: "+request.toString());
        Comite comite = comiteMapper.toEntity(request);
        comite.setDataStatus(DataStatus.CREATED);
        ComiteResponse comiteResponse = comiteMapper.toDto(repository.save(comite));
        log.info("Comite is created with id: {}", comite.getId());
        log.info("Returning ComiteResponse with trackingId: {}", comiteResponse.getTrackingId());
        return comiteResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public ComiteResponse getOne(UUID id) {
        log.info("Searching for Comite with id: "+id);
        Comite comite = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("Comite with id: "+id+" not found!"));
        ComiteResponse comiteResponse = comiteMapper.toDto(comite);
        log.info("Comite is found with id: {}", comite.getId());
        log.info("Returning ComiteResponse with trackingId: {}", comiteResponse.getTrackingId());
        return comiteResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public ComiteResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for Comite with trackingId: "+trackingId);
        Comite comite = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Comite with trackingId: "+trackingId+" not found!"));
        ComiteResponse comiteResponse = comiteMapper.toDto(comite);
        log.info("Comite is found with trackingId: {}", comite.getTrackingId());
        log.info("Returning ComiteResponse with trackingId: {}", comiteResponse.getTrackingId());
        return comiteResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComiteResponse> getAll(int page, int size) {
        log.info("Searching for Comite list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<Comite> comitePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<ComiteResponse> comitePageResponse = comitePage.map(comiteMapper::toDto);
        log.info("Comite list found with size: {}", comitePage.getTotalElements());
        log.info("Returning ComiteResponse list with size: {}", comitePage.getTotalElements());
        return comitePageResponse;
    }

    @Override
    @Transactional
    public ComiteResponse updateOne(ComiteRequest request) {
        log.info("Updating Comite with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("Comite trackingId should not be null !");

        Comite comite = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Comite with id: "+request.getTrackingId()+" not found!"));
        comite.setDataStatus(DataStatus.UPDATED);
        ComiteResponse comiteResponse = comiteMapper.toDto(repository.save(comite));
        log.info("Comite is updated with id: {}", comite.getId());
        log.info("Returning ComiteResponse with trackingId: {}", comiteResponse.getTrackingId());
        return comiteResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting Comite with trackingId:: "+trackingId);
        Comite comite = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Comite with id: "+trackingId+" not found!"));
        comite.setDataStatus(DataStatus.DELETED);
        repository.save(comite);
        log.info("Comite is deleted with id: {}", comite.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("Comite total count : {}", count);
        return count;
    }
}
