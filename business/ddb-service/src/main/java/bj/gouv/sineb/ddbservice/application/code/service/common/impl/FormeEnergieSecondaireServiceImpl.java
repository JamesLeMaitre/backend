package bj.gouv.sineb.ddbservice.application.code.service.common.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.FormeEnergieSecondaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.FormeEnergieSecondaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.FormeEnergieSecondaire;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.FormeEnergieSecondaireMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.FormeEnergieSecondaireRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.FormeEnergieSecondaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class FormeEnergieSecondaireServiceImpl implements FormeEnergieSecondaireService {
    private final FormeEnergieSecondaireRepository repository;
    private final FormeEnergieSecondaireMapper formeEnergieSecondaireMapper;

    public FormeEnergieSecondaireServiceImpl(FormeEnergieSecondaireRepository repository, FormeEnergieSecondaireMapper formeEnergieSecondaireMapper) {
        this.repository = repository;
        this.formeEnergieSecondaireMapper = formeEnergieSecondaireMapper;
    }


    @Override
    @Transactional
    public FormeEnergieSecondaireResponse save(FormeEnergieSecondaireRequest request) {
        log.info("Saving FormeEnergieSecondaire with payload: "+request.toString());
        FormeEnergieSecondaire formeEnergieSecondaire = formeEnergieSecondaireMapper.toEntity(request);
        formeEnergieSecondaire.setDataStatus(DataStatus.CREATED);
        FormeEnergieSecondaireResponse formeEnergieSecondaireResponse = formeEnergieSecondaireMapper.toDto(repository.save(formeEnergieSecondaire));
        log.info("FormeEnergieSecondaire is created with id: {}", formeEnergieSecondaire.getId());
        log.info("Returning FormeEnergieSecondaireResponse with trackingId: {}", formeEnergieSecondaireResponse.getTrackingId());
        return formeEnergieSecondaireResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public FormeEnergieSecondaireResponse getOne(UUID id) {
        log.info("Searching for FormeEnergieSecondaire with id: "+id);
        FormeEnergieSecondaire formeEnergieSecondaire = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("FormeEnergieSecondaire with id: "+id+" not found!"));
        FormeEnergieSecondaireResponse formeEnergieSecondaireResponse = formeEnergieSecondaireMapper.toDto(formeEnergieSecondaire);
        log.info("FormeEnergieSecondaire is found with id: {}", formeEnergieSecondaire.getId());
        log.info("Returning FormeEnergieSecondaireResponse with trackingId: {}", formeEnergieSecondaireResponse.getTrackingId());
        return formeEnergieSecondaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public FormeEnergieSecondaireResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for FormeEnergieSecondaire with trackingId: "+trackingId);
        FormeEnergieSecondaire formeEnergieSecondaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("FormeEnergieSecondaire with trackingId: "+trackingId+" not found!"));
        FormeEnergieSecondaireResponse formeEnergieSecondaireResponse = formeEnergieSecondaireMapper.toDto(formeEnergieSecondaire);
        log.info("FormeEnergieSecondaire is found with trackingId: {}", formeEnergieSecondaire.getTrackingId());
        log.info("Returning FormeEnergieSecondaireResponse with trackingId: {}", formeEnergieSecondaireResponse.getTrackingId());
        return formeEnergieSecondaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormeEnergieSecondaireResponse> getAll(int page, int size) {
        log.info("Searching for FormeEnergieSecondaire list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<FormeEnergieSecondaire> formeEnergieSecondairePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<FormeEnergieSecondaireResponse> formeEnergieSecondairePageResponse = formeEnergieSecondairePage.map(formeEnergieSecondaireMapper::toDto);
        log.info("FormeEnergieSecondaire list found with size: {}", formeEnergieSecondairePage.getTotalElements());
        log.info("Returning FormeEnergieSecondaireResponse list with size: {}", formeEnergieSecondairePage.getTotalElements());
        return formeEnergieSecondairePageResponse;
    }

    @Override
    public Page<FormeEnergieSecondaireResponse> getAllByFormeEnergiePrimaireId(UUID formeEnergiePrimaireTrackingId, int page, int size) {
        log.info("Searching for FormeEnergieSecondaire list by forme energie primaire with (formeEnergiePrimaireTrackingId, page, size, deleted) : "+"("+formeEnergiePrimaireTrackingId+","+page+", "+size+", "+DataStatus.DELETED+")");
        Page<FormeEnergieSecondaire> formeEnergieSecondairePage = repository.findAllByDataStatusIsNotAndAndFormeEnergiePrimaireTrackingId(DataStatus.DELETED, formeEnergiePrimaireTrackingId, PageRequest.of(page, size));
        Page<FormeEnergieSecondaireResponse> formeEnergieSecondairePageResponse = formeEnergieSecondairePage.map(formeEnergieSecondaireMapper::toDto);
        log.info("FormeEnergieSecondaire list found with size: {}", formeEnergieSecondairePage.getTotalElements());
        log.info("Returning FormeEnergieSecondaireResponse list with size: {}", formeEnergieSecondairePage.getTotalElements());
        return formeEnergieSecondairePageResponse;
    }

    @Override
    @Transactional
    public FormeEnergieSecondaireResponse updateOne(FormeEnergieSecondaireRequest request) {
        log.info("Updating FormeEnergieSecondaire with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("FormeEnergieSecondaire trackingId should not be null !");

        FormeEnergieSecondaire formeEnergieSecondaire = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("FormeEnergieSecondaire with id: "+request.getTrackingId()+" not found!"));
        formeEnergieSecondaire.setDataStatus(DataStatus.UPDATED);
        FormeEnergieSecondaireResponse formeEnergieSecondaireResponse = formeEnergieSecondaireMapper.toDto(repository.save(formeEnergieSecondaire));
        log.info("FormeEnergieSecondaire is updated with id: {}", formeEnergieSecondaire.getId());
        log.info("Returning FormeEnergieSecondaireResponse with trackingId: {}", formeEnergieSecondaireResponse.getTrackingId());
        return formeEnergieSecondaireResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting FormeEnergieSecondaire with trackingId:: "+trackingId);
        FormeEnergieSecondaire formeEnergieSecondaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("FormeEnergieSecondaire with id: "+trackingId+" not found!"));
        formeEnergieSecondaire.setDataStatus(DataStatus.DELETED);
        repository.save(formeEnergieSecondaire);
        log.info("FormeEnergieSecondaire is deleted with id: {}", formeEnergieSecondaire.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("FormeEnergieSecondaire total count : {}", count);
        return count;
    }
}
