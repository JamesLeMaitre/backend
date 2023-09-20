package bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.DroitAccesRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.DroitAccesRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.DroitAccesRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.mapper.rc_rbdd.DroitAccesRessourceDocumentaireMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.rc_rbdd.DroitAccesRessourceDocumentaireRepository;
import bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.DroitAccesRessourceDocumentaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class DroitAccesRessourceDocumentaireServiceImpl implements DroitAccesRessourceDocumentaireService {
    private final DroitAccesRessourceDocumentaireRepository repository;
    private final DroitAccesRessourceDocumentaireMapper civiliteMapper;

    public DroitAccesRessourceDocumentaireServiceImpl(DroitAccesRessourceDocumentaireRepository repository, DroitAccesRessourceDocumentaireMapper civiliteMapper) {
        this.repository = repository;
        this.civiliteMapper = civiliteMapper;
    }


    @Override
    @Transactional
    public DroitAccesRessourceDocumentaireResponse save(DroitAccesRessourceDocumentaireRequest request) {
        log.info("Saving DroitAccesRessourceDocumentaire with payload: "+request.toString());
        DroitAccesRessourceDocumentaire droitAccesRessourceDocumentaire = civiliteMapper.toEntity(request);
        droitAccesRessourceDocumentaire.setDataStatus(DataStatus.CREATED);
        DroitAccesRessourceDocumentaireResponse civiliteResponse = civiliteMapper.toDto(repository.save(droitAccesRessourceDocumentaire));
        log.info("DroitAccesRessourceDocumentaire is created with id: {}", droitAccesRessourceDocumentaire.getId());
        log.info("Returning DroitAccesRessourceDocumentaireResponse with trackingId: {}", civiliteResponse.getTrackingId());
        return civiliteResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public DroitAccesRessourceDocumentaireResponse getOne(UUID id) {
        log.info("Searching for DroitAccesRessourceDocumentaire with id: "+id);
        DroitAccesRessourceDocumentaire civilite = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("DroitAccesRessourceDocumentaire with id: "+id+" not found!"));
        DroitAccesRessourceDocumentaireResponse civiliteResponse = civiliteMapper.toDto(civilite);
        log.info("DroitAccesRessourceDocumentaire is found with id: {}", civilite.getId());
        log.info("Returning DroitAccesRessourceDocumentaireResponse with trackingId: {}", civiliteResponse.getTrackingId());
        return civiliteResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public DroitAccesRessourceDocumentaireResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for DroitAccesRessourceDocumentaire with trackingId: "+trackingId);
        DroitAccesRessourceDocumentaire civilite = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("DroitAccesRessourceDocumentaire with trackingId: "+trackingId+" not found!"));
        DroitAccesRessourceDocumentaireResponse civiliteResponse = civiliteMapper.toDto(civilite);
        log.info("DroitAccesRessourceDocumentaire is found with trackingId: {}", civilite.getTrackingId());
        log.info("Returning DroitAccesRessourceDocumentaireResponse with trackingId: {}", civiliteResponse.getTrackingId());
        return civiliteResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DroitAccesRessourceDocumentaireResponse> getAll(int page, int size) {
        log.info("Searching for DroitAccesRessourceDocumentaire list with (page, size, deleted) : "+"("+page+", "+size+", "+ DataStatus.DELETED +")");
        Page<DroitAccesRessourceDocumentaire> civilitePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<DroitAccesRessourceDocumentaireResponse> civilitePageResponse = civilitePage.map(civiliteMapper::toDto);
        log.info("DroitAccesRessourceDocumentaire list found with size: {}", civilitePage.getTotalElements());
        log.info("Returning DroitAccesRessourceDocumentaireResponse list with size: {}", civilitePage.getTotalElements());
        return civilitePageResponse;
    }

    @Override
    @Transactional
    public DroitAccesRessourceDocumentaireResponse updateOne(DroitAccesRessourceDocumentaireRequest request) {
        log.info("Updating DroitAccesRessourceDocumentaire with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("DroitAccesRessourceDocumentaire trackingId should not be null !");

        DroitAccesRessourceDocumentaire droitAccesRessourceDocumentaire = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("DroitAccesRessourceDocumentaire with id: "+request.getTrackingId()+" not found!"));
        droitAccesRessourceDocumentaire.setDataStatus(DataStatus.UPDATED);
        DroitAccesRessourceDocumentaireResponse civiliteResponse = civiliteMapper.toDto(repository.save(droitAccesRessourceDocumentaire));
        log.info("DroitAccesRessourceDocumentaire is updated with id: {}", droitAccesRessourceDocumentaire.getId());
        log.info("Returning DroitAccesRessourceDocumentaireResponse with trackingId: {}", civiliteResponse.getTrackingId());
        return civiliteResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting DroitAccesRessourceDocumentaire with trackingId:: "+trackingId);
        DroitAccesRessourceDocumentaire droitAccesRessourceDocumentaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("DroitAccesRessourceDocumentaire with id: "+trackingId+" not found!"));
        droitAccesRessourceDocumentaire.setDataStatus(DataStatus.DELETED);
        repository.save(droitAccesRessourceDocumentaire);
        log.info("DroitAccesRessourceDocumentaire is deleted with id: {}", droitAccesRessourceDocumentaire.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("DroitAccesRessourceDocumentaire total count : {}", count);
        return count;
    }
}
