package bj.gouv.sineb.ddbservice.application.code.service.common.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.FormeEnergiePrimaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.FormeEnergiePrimaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.FormeEnergiePrimaire;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.FormeEnergiePrimaireMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.FormeEnergiePrimaireRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.FormeEnergiePrimaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class FormeEnergiePrimaireServiceImpl implements FormeEnergiePrimaireService {
    private final FormeEnergiePrimaireRepository repository;
    private final FormeEnergiePrimaireMapper formeEnergiePrimaireMapper;

    public FormeEnergiePrimaireServiceImpl(FormeEnergiePrimaireRepository repository, FormeEnergiePrimaireMapper formeEnergiePrimaireMapper) {
        this.repository = repository;
        this.formeEnergiePrimaireMapper = formeEnergiePrimaireMapper;
    }


    @Override
    @Transactional
    public FormeEnergiePrimaireResponse save(FormeEnergiePrimaireRequest request) {
        log.info("Saving FormeEnergiePrimaire with payload: "+request.toString());
        FormeEnergiePrimaire formeEnergiePrimaire = formeEnergiePrimaireMapper.toEntity(request);
        formeEnergiePrimaire.setDataStatus(DataStatus.CREATED);
        FormeEnergiePrimaireResponse formeEnergiePrimaireResponse = formeEnergiePrimaireMapper.toDto(repository.save(formeEnergiePrimaire));
        log.info("FormeEnergiePrimaire is created with id: {}", formeEnergiePrimaire.getId());
        log.info("Returning FormeEnergiePrimaireResponse with trackingId: {}", formeEnergiePrimaireResponse.getTrackingId());
        return formeEnergiePrimaireResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public FormeEnergiePrimaireResponse getOne(UUID id) {
        log.info("Searching for FormeEnergiePrimaire with id: "+id);
        FormeEnergiePrimaire formeEnergiePrimaire = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("FormeEnergiePrimaire with id: "+id+" not found!"));
        FormeEnergiePrimaireResponse formeEnergiePrimaireResponse = formeEnergiePrimaireMapper.toDto(formeEnergiePrimaire);
        log.info("FormeEnergiePrimaire is found with id: {}", formeEnergiePrimaire.getId());
        log.info("Returning FormeEnergiePrimaireResponse with trackingId: {}", formeEnergiePrimaireResponse.getTrackingId());
        return formeEnergiePrimaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public FormeEnergiePrimaireResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for FormeEnergiePrimaire with trackingId: "+trackingId);
        FormeEnergiePrimaire formeEnergiePrimaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("FormeEnergiePrimaire with trackingId: "+trackingId+" not found!"));
        FormeEnergiePrimaireResponse formeEnergiePrimaireResponse = formeEnergiePrimaireMapper.toDto(formeEnergiePrimaire);
        log.info("FormeEnergiePrimaire is found with trackingId: {}", formeEnergiePrimaire.getTrackingId());
        log.info("Returning FormeEnergiePrimaireResponse with trackingId: {}", formeEnergiePrimaireResponse.getTrackingId());
        return formeEnergiePrimaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormeEnergiePrimaireResponse> getAll(int page, int size) {
        log.info("Searching for FormeEnergiePrimaire list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<FormeEnergiePrimaire> formeEnergiePrimairePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<FormeEnergiePrimaireResponse> formeEnergiePrimairePageResponse = formeEnergiePrimairePage.map(formeEnergiePrimaireMapper::toDto);
        log.info("FormeEnergiePrimaire list found with size: {}", formeEnergiePrimairePage.getTotalElements());
        log.info("Returning FormeEnergiePrimaireResponse list with size: {}", formeEnergiePrimairePage.getTotalElements());
        return formeEnergiePrimairePageResponse;
    }

    @Override
    @Transactional
    public FormeEnergiePrimaireResponse updateOne(FormeEnergiePrimaireRequest request) {
        log.info("Updating FormeEnergiePrimaire with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("FormeEnergiePrimaire trackingId should not be null !");

        FormeEnergiePrimaire formeEnergiePrimaire = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("FormeEnergiePrimaire with id: "+request.getTrackingId()+" not found!"));
        formeEnergiePrimaire.setDataStatus(DataStatus.UPDATED);
        FormeEnergiePrimaireResponse formeEnergiePrimaireResponse = formeEnergiePrimaireMapper.toDto(repository.save(formeEnergiePrimaire));
        log.info("FormeEnergiePrimaire is updated with id: {}", formeEnergiePrimaire.getId());
        log.info("Returning FormeEnergiePrimaireResponse with trackingId: {}", formeEnergiePrimaireResponse.getTrackingId());
        return formeEnergiePrimaireResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting FormeEnergiePrimaire with trackingId:: "+trackingId);
        FormeEnergiePrimaire formeEnergiePrimaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("FormeEnergiePrimaire with id: "+trackingId+" not found!"));
        formeEnergiePrimaire.setDataStatus(DataStatus.DELETED);
        repository.save(formeEnergiePrimaire);
        log.info("FormeEnergiePrimaire is deleted with id: {}", formeEnergiePrimaire.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("FormeEnergiePrimaire total count : {}", count);
        return count;
    }
}
