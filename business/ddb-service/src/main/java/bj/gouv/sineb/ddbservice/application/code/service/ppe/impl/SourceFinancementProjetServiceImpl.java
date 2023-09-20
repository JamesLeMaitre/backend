package bj.gouv.sineb.ddbservice.application.code.service.ppe.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.ppe.SourceFinancementProjetRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.ppe.SourceFinancementProjetResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.ppe.SourceFinancementProjet;
import bj.gouv.sineb.ddbservice.application.code.mapper.ppe.SourceFinancementProjetMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.ppe.SourceFinancementProjetRepository;
import bj.gouv.sineb.ddbservice.application.code.service.ppe.SourceFinancementProjetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class SourceFinancementProjetServiceImpl implements SourceFinancementProjetService {
    private final SourceFinancementProjetRepository repository;
    private final SourceFinancementProjetMapper sourceFinancementProjetMapper;

    public SourceFinancementProjetServiceImpl(SourceFinancementProjetRepository repository, SourceFinancementProjetMapper sourceFinancementProjetMapper) {
        this.repository = repository;
        this.sourceFinancementProjetMapper = sourceFinancementProjetMapper;
    }


    @Override
    @Transactional
    public SourceFinancementProjetResponse save(SourceFinancementProjetRequest request) {
        log.info("Saving SourceFinancementProjet with payload: "+request.toString());
        SourceFinancementProjet sourceFinancementProjet = sourceFinancementProjetMapper.toEntity(request);
        sourceFinancementProjet.setDataStatus(DataStatus.CREATED);
        SourceFinancementProjetResponse sourceFinancementProjetResponse = sourceFinancementProjetMapper.toDto(repository.save(sourceFinancementProjet));
        log.info("SourceFinancementProjet is created with id: {}", sourceFinancementProjet.getId());
        log.info("Returning SourceFinancementProjetResponse with trackingId: {}", sourceFinancementProjetResponse.getTrackingId());
        return sourceFinancementProjetResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public SourceFinancementProjetResponse getOne(UUID id) {
        log.info("Searching for SourceFinancementProjet with id: "+id);
        SourceFinancementProjet sourceFinancementProjet = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("SourceFinancementProjet with id: "+id+" not found!"));
        SourceFinancementProjetResponse sourceFinancementProjetResponse = sourceFinancementProjetMapper.toDto(sourceFinancementProjet);
        log.info("SourceFinancementProjet is found with id: {}", sourceFinancementProjet.getId());
        log.info("Returning SourceFinancementProjetResponse with trackingId: {}", sourceFinancementProjetResponse.getTrackingId());
        return sourceFinancementProjetResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public SourceFinancementProjetResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for SourceFinancementProjet with trackingId: "+trackingId);
        SourceFinancementProjet sourceFinancementProjet = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("SourceFinancementProjet with trackingId: "+trackingId+" not found!"));
        SourceFinancementProjetResponse sourceFinancementProjetResponse = sourceFinancementProjetMapper.toDto(sourceFinancementProjet);
        log.info("SourceFinancementProjet is found with trackingId: {}", sourceFinancementProjet.getTrackingId());
        log.info("Returning SourceFinancementProjetResponse with trackingId: {}", sourceFinancementProjetResponse.getTrackingId());
        return sourceFinancementProjetResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SourceFinancementProjetResponse> getAll(int page, int size) {
        log.info("Searching for SourceFinancementProjet list with (page, size, deleted) : "+"("+page+", "+size+", "+ DataStatus.DELETED +")");
        Page<SourceFinancementProjet> sourceFinancementProjetPage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<SourceFinancementProjetResponse> sourceFinancementProjetPageResponse = sourceFinancementProjetPage.map(sourceFinancementProjetMapper::toDto);
        log.info("SourceFinancementProjet list found with size: {}", sourceFinancementProjetPage.getTotalElements());
        log.info("Returning SourceFinancementProjetResponse list with size: {}", sourceFinancementProjetPage.getTotalElements());
        return sourceFinancementProjetPageResponse;
    }

    @Override
    @Transactional
    public SourceFinancementProjetResponse updateOne(SourceFinancementProjetRequest request) {
        log.info("Updating SourceFinancementProjet with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("SourceFinancementProjet trackingId should not be null !");

        SourceFinancementProjet sourceFinancementProjet = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("SourceFinancementProjet with id: "+request.getTrackingId()+" not found!"));
        sourceFinancementProjet.setDataStatus(DataStatus.UPDATED);
        SourceFinancementProjetResponse sourceFinancementProjetResponse = sourceFinancementProjetMapper.toDto(repository.save(sourceFinancementProjet));
        log.info("SourceFinancementProjet is updated with id: {}", sourceFinancementProjet.getId());
        log.info("Returning SourceFinancementProjetResponse with trackingId: {}", sourceFinancementProjetResponse.getTrackingId());
        return sourceFinancementProjetResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting SourceFinancementProjet with trackingId:: "+trackingId);
        SourceFinancementProjet sourceFinancementProjet = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("SourceFinancementProjet with id: "+trackingId+" not found!"));
        sourceFinancementProjet.setDataStatus(DataStatus.DELETED);
        repository.save(sourceFinancementProjet);
        log.info("SourceFinancementProjet is deleted with id: {}", sourceFinancementProjet.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("SourceFinancementProjet total count : {}", count);
        return count;
    }
}
