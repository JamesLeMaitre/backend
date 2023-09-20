package bj.gouv.sineb.ddbservice.application.code.service.common.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.DomaineInterventionSecondaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.DomaineInterventionSecondaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.DomaineInterventionSecondaire;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.DomaineInterventionSecondaireMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.DomaineInterventionSecondaireRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.DomaineInterventionSecondaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class DomaineInterventionSecondaireServiceImpl implements DomaineInterventionSecondaireService {
    private final DomaineInterventionSecondaireRepository repository;
    private final DomaineInterventionSecondaireMapper domaineInterventionSecondaireMapper;

    public DomaineInterventionSecondaireServiceImpl(DomaineInterventionSecondaireRepository repository, DomaineInterventionSecondaireMapper domaineInterventionSecondaireMapper) {
        this.repository = repository;
        this.domaineInterventionSecondaireMapper = domaineInterventionSecondaireMapper;
    }


    @Override
    @Transactional
    public DomaineInterventionSecondaireResponse save(DomaineInterventionSecondaireRequest request) {
        log.info("Saving DomaineInterventionSecondaire with payload: "+request.toString());
        DomaineInterventionSecondaire domaineInterventionSecondaire = domaineInterventionSecondaireMapper.toEntity(request);
        domaineInterventionSecondaire.setDataStatus(DataStatus.CREATED);
        DomaineInterventionSecondaireResponse domaineInterventionSecondaireResponse = domaineInterventionSecondaireMapper.toDto(repository.save(domaineInterventionSecondaire));
        log.info("DomaineInterventionSecondaire is created with id: {}", domaineInterventionSecondaire.getId());
        log.info("Returning DomaineInterventionSecondaireResponse with trackingId: {}", domaineInterventionSecondaireResponse.getTrackingId());
        return domaineInterventionSecondaireResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public DomaineInterventionSecondaireResponse getOne(UUID id) {
        log.info("Searching for DomaineInterventionSecondaire with id: "+id);
        DomaineInterventionSecondaire domaineInterventionSecondaire = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("DomaineInterventionSecondaire with id: "+id+" not found!"));
        DomaineInterventionSecondaireResponse domaineInterventionSecondaireResponse = domaineInterventionSecondaireMapper.toDto(domaineInterventionSecondaire);
        log.info("DomaineInterventionSecondaire is found with id: {}", domaineInterventionSecondaire.getId());
        log.info("Returning DomaineInterventionSecondaireResponse with trackingId: {}", domaineInterventionSecondaireResponse.getTrackingId());
        return domaineInterventionSecondaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public DomaineInterventionSecondaireResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for DomaineInterventionSecondaire with trackingId: "+trackingId);
        DomaineInterventionSecondaire domaineInterventionSecondaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("DomaineInterventionSecondaire with trackingId: "+trackingId+" not found!"));
        DomaineInterventionSecondaireResponse domaineInterventionSecondaireResponse = domaineInterventionSecondaireMapper.toDto(domaineInterventionSecondaire);
        log.info("DomaineInterventionSecondaire is found with trackingId: {}", domaineInterventionSecondaire.getTrackingId());
        log.info("Returning DomaineInterventionSecondaireResponse with trackingId: {}", domaineInterventionSecondaireResponse.getTrackingId());
        return domaineInterventionSecondaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DomaineInterventionSecondaireResponse> getAll(int page, int size) {
        log.info("Searching for DomaineInterventionSecondaire list with (page, size, deleted) : "+"("+page+", "+size+", "+ DataStatus.DELETED +")");
        Page<DomaineInterventionSecondaire> domaineInterventionSecondairePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<DomaineInterventionSecondaireResponse> domaineInterventionSecondairePageResponse = domaineInterventionSecondairePage.map(domaineInterventionSecondaireMapper::toDto);
        log.info("DomaineInterventionSecondaire list found with size: {}", domaineInterventionSecondairePage.getTotalElements());
        log.info("Returning DomaineInterventionSecondaireResponse list with size: {}", domaineInterventionSecondairePage.getTotalElements());
        return domaineInterventionSecondairePageResponse;
    }


    @Override
    public Page<DomaineInterventionSecondaireResponse> getAllByDomaineInterventionPrincipaleId(UUID domaineInterventionPrincipaleTrackingId, int page, int size) {
        log.info("Searching for DomaineInterventionSecondaire list by domaine intervention principale with (domaineInterventionPrincipaleTrackingId, page, size, deleted) : "+"("+domaineInterventionPrincipaleTrackingId+","+page+", "+size+", "+DataStatus.DELETED+")");
        Page<DomaineInterventionSecondaire> domaineInterventionSecondairePage = repository.findAllByDataStatusIsNotAndDomaineInterventionPrincipaleTrackingId(DataStatus.DELETED, domaineInterventionPrincipaleTrackingId, PageRequest.of(page, size));
        Page<DomaineInterventionSecondaireResponse> domaineInterventionSecondairePageResponse = domaineInterventionSecondairePage.map(domaineInterventionSecondaireMapper::toDto);
        log.info("DomaineInterventionSecondaire list found with size: {}", domaineInterventionSecondairePage.getTotalElements());
        log.info("Returning DomaineInterventionSecondaireResponse list with size: {}", domaineInterventionSecondairePage.getTotalElements());
        return domaineInterventionSecondairePageResponse;
    }

    @Override
    @Transactional
    public DomaineInterventionSecondaireResponse updateOne(DomaineInterventionSecondaireRequest request) {
        log.info("Updating DomaineInterventionSecondaire with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("DomaineInterventionSecondaire trackingId should not be null !");

        DomaineInterventionSecondaire domaineInterventionSecondaire = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("DomaineInterventionSecondaire with id: "+request.getTrackingId()+" not found!"));
        domaineInterventionSecondaire.setDataStatus(DataStatus.UPDATED);
        DomaineInterventionSecondaireResponse domaineInterventionSecondaireResponse = domaineInterventionSecondaireMapper.toDto(repository.save(domaineInterventionSecondaire));
        log.info("DomaineInterventionSecondaire is updated with id: {}", domaineInterventionSecondaire.getId());
        log.info("Returning DomaineInterventionSecondaireResponse with trackingId: {}", domaineInterventionSecondaireResponse.getTrackingId());
        return domaineInterventionSecondaireResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting DomaineInterventionSecondaire with trackingId:: "+trackingId);
        DomaineInterventionSecondaire domaineInterventionSecondaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("DomaineInterventionSecondaire with id: "+trackingId+" not found!"));
        domaineInterventionSecondaire.setDataStatus(DataStatus.DELETED);
        repository.save(domaineInterventionSecondaire);
        log.info("DomaineInterventionSecondaire is deleted with id: {}", domaineInterventionSecondaire.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("DomaineInterventionSecondaire total count : {}", count);
        return count;
    }
}
