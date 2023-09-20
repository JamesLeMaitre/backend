package bj.gouv.sineb.ddbservice.application.code.service.common.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.ArrondissementRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.ArrondissementResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Arrondissement;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.ArrondissementMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.ArrondissementRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.ArrondissementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class ArrondissementServiceImpl implements ArrondissementService {
    private final ArrondissementRepository repository;
    private final ArrondissementMapper arrondissementMapper;

    public ArrondissementServiceImpl(ArrondissementRepository repository, ArrondissementMapper arrondissementMapper) {
        this.repository = repository;
        this.arrondissementMapper = arrondissementMapper;
    }


    @Override
    @Transactional
    public ArrondissementResponse save(ArrondissementRequest request) {
        log.info("Saving Arrondissement with payload: "+request.toString());
        Arrondissement arrondissement = arrondissementMapper.toEntity(request);
        arrondissement.setDataStatus(DataStatus.CREATED);
        ArrondissementResponse arrondissementResponse = arrondissementMapper.toDto(repository.save(arrondissement));
        log.info("Arrondissement is created with id: {}", arrondissement.getId());
        log.info("Returning ArrondissementResponse with trackingId: {}", arrondissementResponse.getTrackingId());
        return arrondissementResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public ArrondissementResponse getOne(UUID id) {
        log.info("Searching for Arrondissement with id: "+id);
        Arrondissement arrondissement = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("Arrondissement with id: "+id+" not found!"));
        ArrondissementResponse arrondissementResponse = arrondissementMapper.toDto(arrondissement);
        log.info("Arrondissement is found with id: {}", arrondissement.getId());
        log.info("Returning ArrondissementResponse with trackingId: {}", arrondissementResponse.getTrackingId());
        return arrondissementResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public ArrondissementResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for Arrondissement with trackingId: "+trackingId);
        Arrondissement arrondissement = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Arrondissement with trackingId: "+trackingId+" not found!"));
        ArrondissementResponse arrondissementResponse = arrondissementMapper.toDto(arrondissement);
        log.info("Arrondissement is found with trackingId: {}", arrondissement.getTrackingId());
        log.info("Returning ArrondissementResponse with trackingId: {}", arrondissementResponse.getTrackingId());
        return arrondissementResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArrondissementResponse> getAll(int page, int size) {
        log.info("Searching for Arrondissement list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<Arrondissement> arrondissementPage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<ArrondissementResponse> arrondissementPageResponse = arrondissementPage.map(arrondissementMapper::toDto);
        log.info("Arrondissement list found with size: {}", arrondissementPage.getTotalElements());
        log.info("Returning ArrondissementResponse list with size: {}", arrondissementPage.getTotalElements());
        return arrondissementPageResponse;
    }

    @Override
    @Transactional
    public ArrondissementResponse updateOne(ArrondissementRequest request) {
        log.info("Updating Arrondissement with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("Arrondissement trackingId should not be null !");

        Arrondissement arrondissement = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Arrondissement with id: "+request.getTrackingId()+" not found!"));
        arrondissement.setDataStatus(DataStatus.UPDATED);
        ArrondissementResponse arrondissementResponse = arrondissementMapper.toDto(repository.save(arrondissement));
        log.info("Arrondissement is updated with id: {}", arrondissement.getId());
        log.info("Returning ArrondissementResponse with trackingId: {}", arrondissementResponse.getTrackingId());
        return arrondissementResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting Arrondissement with trackingId:: "+trackingId);
        Arrondissement arrondissement = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Arrondissement with id: "+trackingId+" not found!"));
        arrondissement.setDataStatus(DataStatus.DELETED);
        repository.save(arrondissement);
        log.info("Arrondissement is deleted with id: {}", arrondissement.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("Arrondissement total count : {}", count);
        return count;
    }
}
