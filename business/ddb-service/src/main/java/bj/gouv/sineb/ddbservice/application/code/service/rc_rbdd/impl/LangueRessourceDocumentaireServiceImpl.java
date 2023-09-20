package bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.LangueRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.LangueRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.LangueRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.mapper.rc_rbdd.LangueRessourceDocumentaireMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.rc_rbdd.LangueRessourceDocumentaireRepository;
import bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.LangueRessourceDocumentaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class LangueRessourceDocumentaireServiceImpl implements LangueRessourceDocumentaireService {
    private final LangueRessourceDocumentaireRepository repository;
    private final LangueRessourceDocumentaireMapper langueRessourceDocumentaireMapper;

    public LangueRessourceDocumentaireServiceImpl(LangueRessourceDocumentaireRepository repository, LangueRessourceDocumentaireMapper langueRessourceDocumentaireMapper) {
        this.repository = repository;
        this.langueRessourceDocumentaireMapper = langueRessourceDocumentaireMapper;
    }


    @Override
    @Transactional
    public LangueRessourceDocumentaireResponse save(LangueRessourceDocumentaireRequest request) {
        log.info("Saving LangueRessourceDocumentaire with payload: "+request.toString());
        LangueRessourceDocumentaire langueRessourceDocumentaire = langueRessourceDocumentaireMapper.toEntity(request);
        langueRessourceDocumentaire.setDataStatus(DataStatus.CREATED);
        LangueRessourceDocumentaireResponse langueRessourceDocumentaireResponse = langueRessourceDocumentaireMapper.toDto(repository.save(langueRessourceDocumentaire));
        log.info("LangueRessourceDocumentaire is created with id: {}", langueRessourceDocumentaire.getId());
        log.info("Returning LangueRessourceDocumentaireResponse with trackingId: {}", langueRessourceDocumentaireResponse.getTrackingId());
        return langueRessourceDocumentaireResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public LangueRessourceDocumentaireResponse getOne(UUID id) {
        log.info("Searching for LangueRessourceDocumentaire with id: "+id);
        LangueRessourceDocumentaire langueRessourceDocumentaire = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("LangueRessourceDocumentaire with id: "+id+" not found!"));
        LangueRessourceDocumentaireResponse langueRessourceDocumentaireResponse = langueRessourceDocumentaireMapper.toDto(langueRessourceDocumentaire);
        log.info("LangueRessourceDocumentaire is found with id: {}", langueRessourceDocumentaire.getId());
        log.info("Returning LangueRessourceDocumentaireResponse with trackingId: {}", langueRessourceDocumentaireResponse.getTrackingId());
        return langueRessourceDocumentaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public LangueRessourceDocumentaireResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for LangueRessourceDocumentaire with trackingId: "+trackingId);
        LangueRessourceDocumentaire langueRessourceDocumentaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("LangueRessourceDocumentaire with trackingId: "+trackingId+" not found!"));
        LangueRessourceDocumentaireResponse langueRessourceDocumentaireResponse = langueRessourceDocumentaireMapper.toDto(langueRessourceDocumentaire);
        log.info("LangueRessourceDocumentaire is found with trackingId: {}", langueRessourceDocumentaire.getTrackingId());
        log.info("Returning LangueRessourceDocumentaireResponse with trackingId: {}", langueRessourceDocumentaireResponse.getTrackingId());
        return langueRessourceDocumentaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LangueRessourceDocumentaireResponse> getAll(int page, int size) {
        log.info("Searching for LangueRessourceDocumentaire list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<LangueRessourceDocumentaire> langueRessourceDocumentairePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<LangueRessourceDocumentaireResponse> langueRessourceDocumentairePageResponse = langueRessourceDocumentairePage.map(langueRessourceDocumentaireMapper::toDto);
        log.info("LangueRessourceDocumentaire list found with size: {}", langueRessourceDocumentairePage.getTotalElements());
        log.info("Returning LangueRessourceDocumentaireResponse list with size: {}", langueRessourceDocumentairePage.getTotalElements());
        return langueRessourceDocumentairePageResponse;
    }

    @Override
    @Transactional
    public LangueRessourceDocumentaireResponse updateOne(LangueRessourceDocumentaireRequest request) {
        log.info("Updating LangueRessourceDocumentaire with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("LangueRessourceDocumentaire trackingId should not be null !");

        LangueRessourceDocumentaire langueRessourceDocumentaire = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("LangueRessourceDocumentaire with id: "+request.getTrackingId()+" not found!"));
        langueRessourceDocumentaire.setDataStatus(DataStatus.UPDATED);
        LangueRessourceDocumentaireResponse langueRessourceDocumentaireResponse = langueRessourceDocumentaireMapper.toDto(repository.save(langueRessourceDocumentaire));
        log.info("LangueRessourceDocumentaire is updated with id: {}", langueRessourceDocumentaire.getId());
        log.info("Returning LangueRessourceDocumentaireResponse with trackingId: {}", langueRessourceDocumentaireResponse.getTrackingId());
        return langueRessourceDocumentaireResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting LangueRessourceDocumentaire with trackingId:: "+trackingId);
        LangueRessourceDocumentaire langueRessourceDocumentaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("LangueRessourceDocumentaire with id: "+trackingId+" not found!"));
        langueRessourceDocumentaire.setDataStatus(DataStatus.DELETED);
        repository.save(langueRessourceDocumentaire);
        log.info("LangueRessourceDocumentaire is deleted with id: {}", langueRessourceDocumentaire.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("LangueRessourceDocumentaire total count : {}", count);
        return count;
    }
}
