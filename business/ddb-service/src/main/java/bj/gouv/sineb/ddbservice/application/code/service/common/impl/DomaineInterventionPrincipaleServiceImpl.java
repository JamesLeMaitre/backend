package bj.gouv.sineb.ddbservice.application.code.service.common.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.DomaineInterventionPrincipaleRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.DomaineInterventionPrincipaleResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.DomaineInterventionPrincipale;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.DomaineInterventionPrincipaleMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.DomaineInterventionPrincipaleRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.DomaineInterventionPrincipaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class DomaineInterventionPrincipaleServiceImpl implements DomaineInterventionPrincipaleService {
    private final DomaineInterventionPrincipaleRepository repository;
    private final DomaineInterventionPrincipaleMapper domaineInterventionPrincipaleMapper;

    public DomaineInterventionPrincipaleServiceImpl(DomaineInterventionPrincipaleRepository repository, DomaineInterventionPrincipaleMapper domaineInterventionPrincipaleMapper) {
        this.repository = repository;
        this.domaineInterventionPrincipaleMapper = domaineInterventionPrincipaleMapper;
    }


    @Override
    @Transactional
    public DomaineInterventionPrincipaleResponse save(DomaineInterventionPrincipaleRequest request) {
        log.info("Saving DomaineInterventionPrincipale with payload: "+request.toString());
        DomaineInterventionPrincipale domaineInterventionPrincipale = domaineInterventionPrincipaleMapper.toEntity(request);
        domaineInterventionPrincipale.setDataStatus(DataStatus.CREATED);
        DomaineInterventionPrincipaleResponse domaineInterventionPrincipaleResponse = domaineInterventionPrincipaleMapper.toDto(repository.save(domaineInterventionPrincipale));
        log.info("DomaineInterventionPrincipale is created with id: {}", domaineInterventionPrincipale.getId());
        log.info("Returning DomaineInterventionPrincipaleResponse with trackingId: {}", domaineInterventionPrincipaleResponse.getTrackingId());
        return domaineInterventionPrincipaleResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public DomaineInterventionPrincipaleResponse getOne(UUID id) {
        log.info("Searching for DomaineInterventionPrincipale with id: "+id);
        DomaineInterventionPrincipale domaineInterventionPrincipale = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("DomaineInterventionPrincipale with id: "+id+" not found!"));
        DomaineInterventionPrincipaleResponse domaineInterventionPrincipaleResponse = domaineInterventionPrincipaleMapper.toDto(domaineInterventionPrincipale);
        log.info("DomaineInterventionPrincipale is found with id: {}", domaineInterventionPrincipale.getId());
        log.info("Returning DomaineInterventionPrincipaleResponse with trackingId: {}", domaineInterventionPrincipaleResponse.getTrackingId());
        return domaineInterventionPrincipaleResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public DomaineInterventionPrincipaleResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for DomaineInterventionPrincipale with trackingId: "+trackingId);
        DomaineInterventionPrincipale domaineInterventionPrincipale = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("DomaineInterventionPrincipale with trackingId: "+trackingId+" not found!"));
        DomaineInterventionPrincipaleResponse domaineInterventionPrincipaleResponse = domaineInterventionPrincipaleMapper.toDto(domaineInterventionPrincipale);
        log.info("DomaineInterventionPrincipale is found with trackingId: {}", domaineInterventionPrincipale.getTrackingId());
        log.info("Returning DomaineInterventionPrincipaleResponse with trackingId: {}", domaineInterventionPrincipaleResponse.getTrackingId());
        return domaineInterventionPrincipaleResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DomaineInterventionPrincipaleResponse> getAll(int page, int size) {
        log.info("Searching for DomaineInterventionPrincipale list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<DomaineInterventionPrincipale> domaineInterventionPrincipalePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<DomaineInterventionPrincipaleResponse> domaineInterventionPrincipalePageResponse = domaineInterventionPrincipalePage.map(domaineInterventionPrincipaleMapper::toDto);
        log.info("DomaineInterventionPrincipale list found with size: {}", domaineInterventionPrincipalePage.getTotalElements());
        log.info("Returning DomaineInterventionPrincipaleResponse list with size: {}", domaineInterventionPrincipalePage.getTotalElements());
        return domaineInterventionPrincipalePageResponse;
    }

    @Override
    @Transactional
    public DomaineInterventionPrincipaleResponse updateOne(DomaineInterventionPrincipaleRequest request) {
        log.info("Updating DomaineInterventionPrincipale with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("DomaineInterventionPrincipale trackingId should not be null !");

        DomaineInterventionPrincipale domaineInterventionPrincipale = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("DomaineInterventionPrincipale with id: "+request.getTrackingId()+" not found!"));
        domaineInterventionPrincipale.setDataStatus(DataStatus.UPDATED);
        DomaineInterventionPrincipaleResponse domaineInterventionPrincipaleResponse = domaineInterventionPrincipaleMapper.toDto(repository.save(domaineInterventionPrincipale));
        log.info("DomaineInterventionPrincipale is updated with id: {}", domaineInterventionPrincipale.getId());
        log.info("Returning DomaineInterventionPrincipaleResponse with trackingId: {}", domaineInterventionPrincipaleResponse.getTrackingId());
        return domaineInterventionPrincipaleResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting DomaineInterventionPrincipale with trackingId:: "+trackingId);
        DomaineInterventionPrincipale domaineInterventionPrincipale = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("DomaineInterventionPrincipale with id: "+trackingId+" not found!"));
        domaineInterventionPrincipale.setDataStatus(DataStatus.DELETED);
        repository.save(domaineInterventionPrincipale);
        log.info("DomaineInterventionPrincipale is deleted with id: {}", domaineInterventionPrincipale.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("DomaineInterventionPrincipale total count : {}", count);
        return count;
    }
}
