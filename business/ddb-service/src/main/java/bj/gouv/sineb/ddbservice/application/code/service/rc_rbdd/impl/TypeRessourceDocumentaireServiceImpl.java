package bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.TypeRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.TypeRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.TypeRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.repository.rc_rbdd.TypeRessourceDocumentaireRepository;
import bj.gouv.sineb.ddbservice.application.code.mapper.rc_rbdd.TypeRessourceDocumentaireMapper;
import bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.TypeRessourceDocumentaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class TypeRessourceDocumentaireServiceImpl implements TypeRessourceDocumentaireService {
    private final TypeRessourceDocumentaireRepository repository;
    private final TypeRessourceDocumentaireMapper typeRessourceDocumentaireMapper;

    public TypeRessourceDocumentaireServiceImpl(TypeRessourceDocumentaireRepository repository, TypeRessourceDocumentaireMapper typeRessourceDocumentaireMapper) {
        this.repository = repository;
        this.typeRessourceDocumentaireMapper = typeRessourceDocumentaireMapper;
    }


    @Override
    @Transactional
    public TypeRessourceDocumentaireResponse save(TypeRessourceDocumentaireRequest request) {
        log.info("Saving TypeRessourceDocumentaire with payload: "+request.toString());
        TypeRessourceDocumentaire typeRessourceDocumentaire = typeRessourceDocumentaireMapper.toEntity(request);
        typeRessourceDocumentaire.setDataStatus(DataStatus.CREATED);
        TypeRessourceDocumentaireResponse typeRessourceDocumentaireResponse = typeRessourceDocumentaireMapper.toDto(repository.save(typeRessourceDocumentaire));
        log.info("TypeRessourceDocumentaire is created with id: {}", typeRessourceDocumentaire.getId());
        log.info("Returning TypeRessourceDocumentaireResponse with trackingId: {}", typeRessourceDocumentaireResponse.getTrackingId());
        return typeRessourceDocumentaireResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public TypeRessourceDocumentaireResponse getOne(UUID id) {
        log.info("Searching for TypeRessourceDocumentaire with id: "+id);
        TypeRessourceDocumentaire typeRessourceDocumentaire = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("TypeRessourceDocumentaire with id: "+id+" not found!"));
        TypeRessourceDocumentaireResponse typeRessourceDocumentaireResponse = typeRessourceDocumentaireMapper.toDto(typeRessourceDocumentaire);
        log.info("TypeRessourceDocumentaire is found with id: {}", typeRessourceDocumentaire.getId());
        log.info("Returning TypeRessourceDocumentaireResponse with trackingId: {}", typeRessourceDocumentaireResponse.getTrackingId());
        return typeRessourceDocumentaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public TypeRessourceDocumentaireResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for TypeRessourceDocumentaire with trackingId: "+trackingId);
        TypeRessourceDocumentaire typeRessourceDocumentaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("TypeRessourceDocumentaire with trackingId: "+trackingId+" not found!"));
        TypeRessourceDocumentaireResponse typeRessourceDocumentaireResponse = typeRessourceDocumentaireMapper.toDto(typeRessourceDocumentaire);
        log.info("TypeRessourceDocumentaire is found with trackingId: {}", typeRessourceDocumentaire.getTrackingId());
        log.info("Returning TypeRessourceDocumentaireResponse with trackingId: {}", typeRessourceDocumentaireResponse.getTrackingId());
        return typeRessourceDocumentaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeRessourceDocumentaireResponse> getAll(int page, int size) {
        log.info("Searching for TypeRessourceDocumentaire list with (page, size, deleted) : "+"("+page+", "+size+", "+ DataStatus.DELETED +")");
        Page<TypeRessourceDocumentaire> typeRessourceDocumentairePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<TypeRessourceDocumentaireResponse> typeRessourceDocumentairePageResponse = typeRessourceDocumentairePage.map(typeRessourceDocumentaireMapper::toDto);
        log.info("TypeRessourceDocumentaire list found with size: {}", typeRessourceDocumentairePage.getTotalElements());
        log.info("Returning TypeRessourceDocumentaireResponse list with size: {}", typeRessourceDocumentairePage.getTotalElements());
        return typeRessourceDocumentairePageResponse;
    }

    @Override
    @Transactional
    public TypeRessourceDocumentaireResponse updateOne(TypeRessourceDocumentaireRequest request) {
        log.info("Updating TypeRessourceDocumentaire with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("TypeRessourceDocumentaire trackingId should not be null !");

        TypeRessourceDocumentaire typeRessourceDocumentaire = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("TypeRessourceDocumentaire with id: "+request.getTrackingId()+" not found!"));
        typeRessourceDocumentaire.setDataStatus(DataStatus.UPDATED);
        TypeRessourceDocumentaireResponse typeRessourceDocumentaireResponse = typeRessourceDocumentaireMapper.toDto(repository.save(typeRessourceDocumentaire));
        log.info("TypeRessourceDocumentaire is updated with id: {}", typeRessourceDocumentaire.getId());
        log.info("Returning TypeRessourceDocumentaireResponse with trackingId: {}", typeRessourceDocumentaireResponse.getTrackingId());
        return typeRessourceDocumentaireResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting TypeRessourceDocumentaire with trackingId:: "+trackingId);
        TypeRessourceDocumentaire typeRessourceDocumentaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("TypeRessourceDocumentaire with id: "+trackingId+" not found!"));
        typeRessourceDocumentaire.setDataStatus(DataStatus.DELETED);
        repository.save(typeRessourceDocumentaire);
        log.info("TypeRessourceDocumentaire is deleted with id: {}", typeRessourceDocumentaire.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("TypeRessourceDocumentaire total count : {}", count);
        return count;
    }
}
