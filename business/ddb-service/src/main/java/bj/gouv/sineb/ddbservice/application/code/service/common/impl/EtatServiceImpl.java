package bj.gouv.sineb.ddbservice.application.code.service.common.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.EtatRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.EtatResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Etat;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.EtatMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.EtatRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.EtatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class EtatServiceImpl implements EtatService {
    private final EtatRepository repository;
    private final EtatMapper etatMapper;

    public EtatServiceImpl(EtatRepository repository, EtatMapper etatMapper) {
        this.repository = repository;
        this.etatMapper = etatMapper;
    }


    @Override
    @Transactional
    public EtatResponse save(EtatRequest request) {
        log.info("Saving Etat with payload: "+request.toString());
        Etat etat = etatMapper.toEntity(request);
        etat.setDataStatus(DataStatus.CREATED);
        EtatResponse etatResponse = etatMapper.toDto(repository.save(etat));
        log.info("Etat is created with id: {}", etat.getId());
        log.info("Returning EtatResponse with trackingId: {}", etatResponse.getTrackingId());
        return etatResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public EtatResponse getOne(UUID id) {
        log.info("Searching for Etat with id: "+id);
        Etat etat = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("Etat with id: "+id+" not found!"));
        EtatResponse etatResponse = etatMapper.toDto(etat);
        log.info("Etat is found with id: {}", etat.getId());
        log.info("Returning EtatResponse with trackingId: {}", etatResponse.getTrackingId());
        return etatResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public EtatResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for Etat with trackingId: "+trackingId);
        Etat etat = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Etat with trackingId: "+trackingId+" not found!"));
        EtatResponse etatResponse = etatMapper.toDto(etat);
        log.info("Etat is found with trackingId: {}", etat.getTrackingId());
        log.info("Returning EtatResponse with trackingId: {}", etatResponse.getTrackingId());
        return etatResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EtatResponse> getAll(int page, int size) {
        log.info("Searching for Etat list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<Etat> etatPage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<EtatResponse> etatPageResponse = etatPage.map(etatMapper::toDto);
        log.info("Etat list found with size: {}", etatPage.getTotalElements());
        log.info("Returning EtatResponse list with size: {}", etatPage.getTotalElements());
        return etatPageResponse;
    }

    @Override
    @Transactional
    public EtatResponse updateOne(EtatRequest request) {
        log.info("Updating Etat with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("Etat trackingId should not be null !");

        Etat etat = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Etat with id: "+request.getTrackingId()+" not found!"));
        etat.setDataStatus(DataStatus.UPDATED);
        EtatResponse etatResponse = etatMapper.toDto(repository.save(etat));
        log.info("Etat is updated with id: {}", etat.getId());
        log.info("Returning EtatResponse with trackingId: {}", etatResponse.getTrackingId());
        return etatResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting Etat with trackingId:: "+trackingId);
        Etat etat = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Etat with id: "+trackingId+" not found!"));
        etat.setDataStatus(DataStatus.DELETED);
        repository.save(etat);
        log.info("Etat is deleted with id: {}", etat.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("Etat total count : {}", count);
        return count;
    }
}
