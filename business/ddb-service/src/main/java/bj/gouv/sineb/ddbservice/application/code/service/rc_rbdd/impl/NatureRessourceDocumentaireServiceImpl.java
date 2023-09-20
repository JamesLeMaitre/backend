package bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.NatureRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.NatureRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.NatureRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.mapper.rc_rbdd.NatureRessourceDocumentaireMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.rc_rbdd.NatureRessourceDocumentaireRepository;
import bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.NatureRessourceDocumentaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class NatureRessourceDocumentaireServiceImpl implements NatureRessourceDocumentaireService {
    private final NatureRessourceDocumentaireRepository repository;
    private final NatureRessourceDocumentaireMapper natureRessourceDocumentaireMapper;

    public NatureRessourceDocumentaireServiceImpl(NatureRessourceDocumentaireRepository repository, NatureRessourceDocumentaireMapper natureRessourceDocumentaireMapper) {
        this.repository = repository;
        this.natureRessourceDocumentaireMapper = natureRessourceDocumentaireMapper;
    }


    @Override
    @Transactional
    public NatureRessourceDocumentaireResponse save(NatureRessourceDocumentaireRequest request) {
        log.info("Saving NatureRessourceDocumentaire with payload: "+request.toString());
        NatureRessourceDocumentaire natureRessourceDocumentaire = natureRessourceDocumentaireMapper.toEntity(request);
        natureRessourceDocumentaire.setDataStatus(DataStatus.CREATED);
        NatureRessourceDocumentaireResponse natureRessourceDocumentaireResponse = natureRessourceDocumentaireMapper.toDto(repository.save(natureRessourceDocumentaire));
        log.info("NatureRessourceDocumentaire is created with id: {}", natureRessourceDocumentaire.getId());
        log.info("Returning NatureRessourceDocumentaireResponse with trackingId: {}", natureRessourceDocumentaireResponse.getTrackingId());
        return natureRessourceDocumentaireResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public NatureRessourceDocumentaireResponse getOne(UUID id) {
        log.info("Searching for NatureRessourceDocumentaire with id: "+id);
        NatureRessourceDocumentaire natureRessourceDocumentaire = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("NatureRessourceDocumentaire with id: "+id+" not found!"));
        NatureRessourceDocumentaireResponse natureRessourceDocumentaireResponse = natureRessourceDocumentaireMapper.toDto(natureRessourceDocumentaire);
        log.info("NatureRessourceDocumentaire is found with id: {}", natureRessourceDocumentaire.getId());
        log.info("Returning NatureRessourceDocumentaireResponse with trackingId: {}", natureRessourceDocumentaireResponse.getTrackingId());
        return natureRessourceDocumentaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public NatureRessourceDocumentaireResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for NatureRessourceDocumentaire with trackingId: "+trackingId);
        NatureRessourceDocumentaire natureRessourceDocumentaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("NatureRessourceDocumentaire with trackingId: "+trackingId+" not found!"));
        NatureRessourceDocumentaireResponse natureRessourceDocumentaireResponse = natureRessourceDocumentaireMapper.toDto(natureRessourceDocumentaire);
        log.info("NatureRessourceDocumentaire is found with trackingId: {}", natureRessourceDocumentaire.getTrackingId());
        log.info("Returning NatureRessourceDocumentaireResponse with trackingId: {}", natureRessourceDocumentaireResponse.getTrackingId());
        return natureRessourceDocumentaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NatureRessourceDocumentaireResponse> getAll(int page, int size) {
        log.info("Searching for NatureRessourceDocumentaire list with (page, size, deleted) : "+"("+page+", "+size+", "+ DataStatus.DELETED +")");
        Page<NatureRessourceDocumentaire> natureRessourceDocumentairePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<NatureRessourceDocumentaireResponse> natureRessourceDocumentairePageResponse = natureRessourceDocumentairePage.map(natureRessourceDocumentaireMapper::toDto);
        log.info("NatureRessourceDocumentaire list found with size: {}", natureRessourceDocumentairePage.getTotalElements());
        log.info("Returning NatureRessourceDocumentaireResponse list with size: {}", natureRessourceDocumentairePage.getTotalElements());
        return natureRessourceDocumentairePageResponse;
    }

    @Override
    @Transactional
    public NatureRessourceDocumentaireResponse updateOne(NatureRessourceDocumentaireRequest request) {
        log.info("Updating NatureRessourceDocumentaire with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("NatureRessourceDocumentaire trackingId should not be null !");

        NatureRessourceDocumentaire natureRessourceDocumentaire = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("NatureRessourceDocumentaire with id: "+request.getTrackingId()+" not found!"));
        natureRessourceDocumentaire.setDataStatus(DataStatus.UPDATED);
        NatureRessourceDocumentaireResponse natureRessourceDocumentaireResponse = natureRessourceDocumentaireMapper.toDto(repository.save(natureRessourceDocumentaire));
        log.info("NatureRessourceDocumentaire is updated with id: {}", natureRessourceDocumentaire.getId());
        log.info("Returning NatureRessourceDocumentaireResponse with trackingId: {}", natureRessourceDocumentaireResponse.getTrackingId());
        return natureRessourceDocumentaireResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting NatureRessourceDocumentaire with trackingId:: "+trackingId);
        NatureRessourceDocumentaire natureRessourceDocumentaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("NatureRessourceDocumentaire with id: "+trackingId+" not found!"));
        natureRessourceDocumentaire.setDataStatus(DataStatus.DELETED);
        repository.save(natureRessourceDocumentaire);
        log.info("NatureRessourceDocumentaire is deleted with id: {}", natureRessourceDocumentaire.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("NatureRessourceDocumentaire total count : {}", count);
        return count;
    }
}
