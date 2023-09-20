package bj.gouv.sineb.ddbservice.application.code.service.aae.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.CiviliteRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.CiviliteResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.Civilite;
import bj.gouv.sineb.ddbservice.application.code.mapper.aae.CiviliteMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.aae.CiviliteRepository;
import bj.gouv.sineb.ddbservice.application.code.service.aae.CiviliteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.function.Supplier;

@Slf4j
@Service
public class CiviliteServiceImpl implements CiviliteService {

    private final CiviliteRepository repository;
    private final CiviliteMapper civiliteMapper;

    private final StreamBridge bridge;


    public CiviliteServiceImpl(CiviliteRepository repository, CiviliteMapper civiliteMapper, StreamBridge bridge) {
        this.repository = repository;
        this.civiliteMapper = civiliteMapper;
        this.bridge = bridge;
    }


    @Override
    @Transactional
    public CiviliteResponse save(CiviliteRequest request) {
        log.info("Saving Civilite with payload: "+request.toString());
        Civilite civilite = civiliteMapper.toEntity(request);
        civilite.setTrackingId(UUID.randomUUID());
        CiviliteResponse civiliteResponse = civiliteMapper.toDto(repository.save(civilite));
        //Send into kafka
        producer(civiliteResponse);
        log.info("Civilite is created with id: {}", civilite.getId());
        log.info("Returning CiviliteResponse with trackingId: {}", civiliteResponse.getTrackingId());
        return civiliteResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public CiviliteResponse getOne(UUID id) {
        log.info("Searching for Civilite with id: "+id);
        Civilite civilite = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("Civilite with id: "+id+" not found!"));
        CiviliteResponse civiliteResponse = civiliteMapper.toDto(civilite);
        log.info("Civilite is found with id: {}", civilite.getId());
        log.info("Returning CiviliteResponse with trackingId: {}", civiliteResponse.getTrackingId());
        return civiliteResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public CiviliteResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for Civilite with trackingId: "+trackingId);
        Civilite civilite = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Civilite with trackingId: "+trackingId+" not found!"));
        CiviliteResponse civiliteResponse = civiliteMapper.toDto(civilite);
        log.info("Civilite is found with trackingId: {}", civilite.getTrackingId());
        log.info("Returning CiviliteResponse with trackingId: {}", civiliteResponse.getTrackingId());
        return civiliteResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CiviliteResponse> getAll(int page, int size) {
        log.info("Searching for Civilite list with (page, size, deleted) : "+"("+page+", "+size+")");
        Page<Civilite> civilitePage = repository.findByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<CiviliteResponse> civilitePageResponse = civilitePage.map(civiliteMapper::toDto);
        log.info("Civilite list found with size: {}", civilitePage.getTotalElements());
        log.info("Returning CiviliteResponse list with size: {}", civilitePage.getTotalElements());
        return civilitePageResponse;
    }

    @Override
    @Transactional
    public CiviliteResponse updateOne(CiviliteRequest request, UUID trackingId) {
        log.info("Updating Civilite with payload: "+request.toString());

        if (trackingId==null)
            throw new CustomException("Civilite trackingId should not be null !");

        Civilite civilite = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Civilite with id: "+request.getTrackingId()+" not found!"));
        civilite.setDataStatus(DataStatus.UPDATED);
        CiviliteResponse civiliteResponse = civiliteMapper.toDto(repository.save(civilite));
        //Send into kafka
        producer(civiliteResponse);
        log.info("Civilite is updated with id: {}", civilite.getId());
        log.info("Returning CiviliteResponse with trackingId: {}", civiliteResponse.getTrackingId());
        return civiliteResponse;
    }

    @Override
    @Transactional
    public CiviliteResponse deleteOne(UUID trackingId) {
        log.info("Deleting Civilite with trackingId:: "+trackingId);
        Civilite civilite = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Civilite with id: "+trackingId+" not found!"));
        civilite.setDataStatus(DataStatus.DELETED);
       Civilite civiliteSave = repository.save(civilite);

       CiviliteResponse civiliteResponse = civiliteMapper.toDto(civiliteSave);
        //Send into kafka
        producer(civiliteResponse);
        log.info("Civilite is deleted with id: {}", civilite.getId());
        return civiliteResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countByDataStatusIsNot(DataStatus.DELETED);
        log.info("Civilite total count : {}", count);
        return count;
    }

    private void producer(CiviliteResponse response) {
        bridge.send("civility-out-0", response);
    }
}
