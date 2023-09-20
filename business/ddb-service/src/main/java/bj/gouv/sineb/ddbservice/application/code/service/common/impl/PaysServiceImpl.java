package bj.gouv.sineb.ddbservice.application.code.service.common.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.PaysRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.PaysResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Pays;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.PaysMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.PaysRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.PaysService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class PaysServiceImpl implements PaysService {
    private final PaysRepository repository;
    private final PaysMapper mapper;
    
    private final StreamBridge bridge;

    public PaysServiceImpl(PaysRepository repository, PaysMapper mapper, StreamBridge bridge) {
        this.repository = repository;
        this.mapper = mapper;
        this.bridge = bridge;
    }


    @Override
    @Transactional
    public PaysResponse save(PaysRequest request) {
        log.info("Saving Pays with payload: "+request.toString());
        Pays pays = mapper.toEntity(request);
        pays.setTrackingId(UUID.randomUUID());
        pays.setDateCreated(LocalDateTime.now());
        pays.setDateUpdated(LocalDateTime.now());
        PaysResponse paysResponse = mapper.toDto(repository.save(pays));
        //send to kafka
        producer(paysResponse);
        log.info("Pays is created with id: {}", pays.getId());
        log.info("Returning PaysResponse with trackingId: {}", paysResponse.getTrackingId());
        return paysResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public PaysResponse getOne(UUID id) {
        log.info("Searching for Pays with id: "+id);
        Pays civilite = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("Pays with id: "+id+" not found!"));
        PaysResponse paysResponse = mapper.toDto(civilite);
        log.info("Pays is found with id: {}", civilite.getId());
        log.info("Returning PaysResponse with trackingId: {}", paysResponse.getTrackingId());
        return paysResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public PaysResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for Pays with trackingId: "+trackingId);
        Pays civilite = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Pays with trackingId: "+trackingId+" not found!"));
        PaysResponse paysResponse = mapper.toDto(civilite);
        log.info("Pays is found with trackingId: {}", civilite.getTrackingId());
        log.info("Returning PaysResponse with trackingId: {}", paysResponse.getTrackingId());
        return paysResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaysResponse> getAll(int page, int size) {
        log.info("Searching for Pays list with (page, size, deleted) : "+"("+page+", "+size+")");
        Page<Pays> civilitePage = repository.findByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<PaysResponse> paysResponse = civilitePage.map(mapper::toDto);
        log.info("Pays list found with size: {}", civilitePage.getTotalElements());
        log.info("Returning PaysResponse list with size: {}", civilitePage.getTotalElements());
        return paysResponse;
    }

    @Override
    @Transactional
    public PaysResponse updateOne(PaysRequest request, UUID trackingId) {
        log.info("Updating Pays with payload: "+trackingId);

        if (trackingId==null)
            throw new CustomException("Pays trackingId should not be null !");

        Pays pays = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Pays with id: "+trackingId+" not found!"));
        pays.setDataStatus(DataStatus.UPDATED);
        pays.setDateUpdated(LocalDateTime.now());
        PaysResponse paysResponse = mapper.toDto(repository.save(pays));
        //send to kafka
        producer(paysResponse);
        log.info("Pays is updated with id: {}", pays.getId());
        log.info("Returning PaysResponse with trackingId: {}", paysResponse.getTrackingId());
        return paysResponse;
    }

    @Override
    @Transactional
    public PaysResponse deleteOne(UUID trackingId) {
        log.info("Deleting Pays with trackingId:: "+trackingId);
        Pays pays = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Pays with id: "+trackingId+" not found!"));
       pays.setDataStatus(DataStatus.DELETED);
       Pays paysSave = repository.save(pays);
       PaysResponse paysResponse = mapper.toDto(paysSave);
        //send to kafka
        producer(paysResponse);
        log.info("Pays is deleted with id: {}", pays.getId());
        return paysResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countByDataStatusIsNot(DataStatus.DELETED);
        log.info("Pays total count : {}", count);
        return count;
    }
    
    private void producer(PaysResponse response) {
        bridge.send("pays-out-0", response); 
    }
}
