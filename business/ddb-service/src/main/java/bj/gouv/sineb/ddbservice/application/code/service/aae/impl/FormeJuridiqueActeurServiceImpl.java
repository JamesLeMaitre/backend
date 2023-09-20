package bj.gouv.sineb.ddbservice.application.code.service.aae.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.FormeJuridiqueActeurRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.FormeJuridiqueActeurResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.FormeJuridiqueActeur;
import bj.gouv.sineb.ddbservice.application.code.mapper.aae.FormeJuridiqueActeurMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.aae.FormeJuridiqueActeurRepository;
import bj.gouv.sineb.ddbservice.application.code.service.aae.FormeJuridiqueActeurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Slf4j
@Service
public class FormeJuridiqueActeurServiceImpl implements FormeJuridiqueActeurService {
    private final FormeJuridiqueActeurRepository repository;
    private final FormeJuridiqueActeurMapper formeJuridiqueActeurMapper;

    public FormeJuridiqueActeurServiceImpl(FormeJuridiqueActeurRepository repository, FormeJuridiqueActeurMapper formeJuridiqueActeurMapper) {
        this.repository = repository;
        this.formeJuridiqueActeurMapper = formeJuridiqueActeurMapper;
    }


    @Override
    @Transactional
    public FormeJuridiqueActeurResponse save(FormeJuridiqueActeurRequest request) {
        log.info("Saving FormeJuridiqueActeur with payload: "+request.toString());
        FormeJuridiqueActeur formeJuridiqueActeur = formeJuridiqueActeurMapper.toEntity(request);
        formeJuridiqueActeur.setDataStatus(DataStatus.CREATED);
        FormeJuridiqueActeurResponse formeJuridiqueActeurResponse = formeJuridiqueActeurMapper.toDto(repository.save(formeJuridiqueActeur));
        log.info("FormeJuridiqueActeur is created with id: {}", formeJuridiqueActeur.getId());
        log.info("Returning FormeJuridiqueActeurResponse with trackingId: {}", formeJuridiqueActeurResponse.getTrackingId());
        return formeJuridiqueActeurResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public FormeJuridiqueActeurResponse getOne(UUID id) {
        log.info("Searching for FormeJuridiqueActeur with id: "+id);
        FormeJuridiqueActeur formeJuridiqueActeur = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("FormeJuridiqueActeur with id: "+id+" not found!"));
        FormeJuridiqueActeurResponse formeJuridiqueActeurResponse = formeJuridiqueActeurMapper.toDto(formeJuridiqueActeur);
        log.info("FormeJuridiqueActeur is found with id: {}", formeJuridiqueActeur.getId());
        log.info("Returning FormeJuridiqueActeurResponse with trackingId: {}", formeJuridiqueActeurResponse.getTrackingId());
        return formeJuridiqueActeurResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public FormeJuridiqueActeurResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for FormeJuridiqueActeur with trackingId: "+trackingId);
        FormeJuridiqueActeur formeJuridiqueActeur = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("FormeJuridiqueActeur with trackingId: "+trackingId+" not found!"));
        FormeJuridiqueActeurResponse formeJuridiqueActeurResponse = formeJuridiqueActeurMapper.toDto(formeJuridiqueActeur);
        log.info("FormeJuridiqueActeur is found with trackingId: {}", formeJuridiqueActeur.getTrackingId());
        log.info("Returning FormeJuridiqueActeurResponse with trackingId: {}", formeJuridiqueActeurResponse.getTrackingId());
        return formeJuridiqueActeurResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormeJuridiqueActeurResponse> getAll(int page, int size) {
        log.info("Searching for FormeJuridiqueActeur list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<FormeJuridiqueActeur> formeJuridiqueActeurPage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<FormeJuridiqueActeurResponse> formeJuridiqueActeurPageResponse = formeJuridiqueActeurPage.map(formeJuridiqueActeurMapper::toDto);
        log.info("FormeJuridiqueActeur list found with size: {}", formeJuridiqueActeurPage.getTotalElements());
        log.info("Returning FormeJuridiqueActeurResponse list with size: {}", formeJuridiqueActeurPage.getTotalElements());
        return formeJuridiqueActeurPageResponse;
    }

    @Override
    @Transactional
    public FormeJuridiqueActeurResponse updateOne(FormeJuridiqueActeurRequest request) {
        log.info("Updating FormeJuridiqueActeur with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("FormeJuridiqueActeur trackingId should not be null !");

        FormeJuridiqueActeur formeJuridiqueActeur = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("FormeJuridiqueActeur with id: "+request.getTrackingId()+" not found!"));
        formeJuridiqueActeur.setDataStatus(DataStatus.UPDATED);
        FormeJuridiqueActeurResponse formeJuridiqueActeurResponse = formeJuridiqueActeurMapper.toDto(repository.save(formeJuridiqueActeur));
        log.info("FormeJuridiqueActeur is updated with id: {}", formeJuridiqueActeur.getId());
        log.info("Returning FormeJuridiqueActeurResponse with trackingId: {}", formeJuridiqueActeurResponse.getTrackingId());
        return formeJuridiqueActeurResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting FormeJuridiqueActeur with trackingId:: "+trackingId);
        FormeJuridiqueActeur formeJuridiqueActeur = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("FormeJuridiqueActeur with id: "+trackingId+" not found!"));
        formeJuridiqueActeur.setDataStatus(DataStatus.DELETED);
        repository.save(formeJuridiqueActeur);
        log.info("FormeJuridiqueActeur is deleted with id: {}", formeJuridiqueActeur.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("FormeJuridiqueActeur total  count  : {}", count);
        return count;
    }
}
