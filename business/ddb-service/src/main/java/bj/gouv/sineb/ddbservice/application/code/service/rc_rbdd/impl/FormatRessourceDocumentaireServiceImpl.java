package bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.FormatRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.FormatRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.FormatRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.mapper.rc_rbdd.FormatRessourceDocumentaireMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.rc_rbdd.FormatRessourceDocumentaireRepository;
import bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.FormatRessourceDocumentaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class FormatRessourceDocumentaireServiceImpl implements FormatRessourceDocumentaireService {
    private final FormatRessourceDocumentaireRepository repository;
    private final FormatRessourceDocumentaireMapper formatRessourceDocumentaireMapper;

    public FormatRessourceDocumentaireServiceImpl(FormatRessourceDocumentaireRepository repository, FormatRessourceDocumentaireMapper formatRessourceDocumentaireMapper) {
        this.repository = repository;
        this.formatRessourceDocumentaireMapper = formatRessourceDocumentaireMapper;
    }


    @Override
    @Transactional
    public FormatRessourceDocumentaireResponse save(FormatRessourceDocumentaireRequest request) {
        log.info("Saving FormatRessourceDocumentaire with payload: "+request.toString());
        FormatRessourceDocumentaire formatRessourceDocumentaire = formatRessourceDocumentaireMapper.toEntity(request);
        formatRessourceDocumentaire.setDataStatus(DataStatus.CREATED);
        FormatRessourceDocumentaireResponse formatRessourceDocumentaireResponse = formatRessourceDocumentaireMapper.toDto(repository.save(formatRessourceDocumentaire));
        log.info("FormatRessourceDocumentaire is created with id: {}", formatRessourceDocumentaire.getId());
        log.info("Returning FormatRessourceDocumentaireResponse with trackingId: {}", formatRessourceDocumentaireResponse.getTrackingId());
        return formatRessourceDocumentaireResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public FormatRessourceDocumentaireResponse getOne(UUID id) {
        log.info("Searching for FormatRessourceDocumentaire with id: "+id);
        FormatRessourceDocumentaire formatRessourceDocumentaire = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("FormatRessourceDocumentaire with id: "+id+" not found!"));
        FormatRessourceDocumentaireResponse formatRessourceDocumentaireResponse = formatRessourceDocumentaireMapper.toDto(formatRessourceDocumentaire);
        log.info("FormatRessourceDocumentaire is found with id: {}", formatRessourceDocumentaire.getId());
        log.info("Returning FormatRessourceDocumentaireResponse with trackingId: {}", formatRessourceDocumentaireResponse.getTrackingId());
        return formatRessourceDocumentaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public FormatRessourceDocumentaireResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for FormatRessourceDocumentaire with trackingId: "+trackingId);
        FormatRessourceDocumentaire formatRessourceDocumentaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("FormatRessourceDocumentaire with trackingId: "+trackingId+" not found!"));
        FormatRessourceDocumentaireResponse formatRessourceDocumentaireResponse = formatRessourceDocumentaireMapper.toDto(formatRessourceDocumentaire);
        log.info("FormatRessourceDocumentaire is found with trackingId: {}", formatRessourceDocumentaire.getTrackingId());
        log.info("Returning FormatRessourceDocumentaireResponse with trackingId: {}", formatRessourceDocumentaireResponse.getTrackingId());
        return formatRessourceDocumentaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormatRessourceDocumentaireResponse> getAll(int page, int size) {
        log.info("Searching for FormatRessourceDocumentaire list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<FormatRessourceDocumentaire> formatRessourceDocumentairePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<FormatRessourceDocumentaireResponse> formatRessourceDocumentairePageResponse = formatRessourceDocumentairePage.map(formatRessourceDocumentaireMapper::toDto);
        log.info("FormatRessourceDocumentaire list found with size: {}", formatRessourceDocumentairePage.getTotalElements());
        log.info("Returning FormatRessourceDocumentaireResponse list with size: {}", formatRessourceDocumentairePage.getTotalElements());
        return formatRessourceDocumentairePageResponse;
    }

    @Override
    @Transactional
    public FormatRessourceDocumentaireResponse updateOne(FormatRessourceDocumentaireRequest request) {
        log.info("Updating FormatRessourceDocumentaire with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("FormatRessourceDocumentaire trackingId should not be null !");

        FormatRessourceDocumentaire formatRessourceDocumentaire = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("FormatRessourceDocumentaire with id: "+request.getTrackingId()+" not found!"));
        formatRessourceDocumentaire.setDataStatus(DataStatus.UPDATED);
        FormatRessourceDocumentaireResponse formatRessourceDocumentaireResponse = formatRessourceDocumentaireMapper.toDto(repository.save(formatRessourceDocumentaire));
        log.info("FormatRessourceDocumentaire is updated with id: {}", formatRessourceDocumentaire.getId());
        log.info("Returning FormatRessourceDocumentaireResponse with trackingId: {}", formatRessourceDocumentaireResponse.getTrackingId());
        return formatRessourceDocumentaireResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting FormatRessourceDocumentaire with trackingId:: "+trackingId);
        FormatRessourceDocumentaire formatRessourceDocumentaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("FormatRessourceDocumentaire with id: "+trackingId+" not found!"));
        formatRessourceDocumentaire.setDataStatus(DataStatus.DELETED);
        repository.save(formatRessourceDocumentaire);
        log.info("FormatRessourceDocumentaire is deleted with id: {}", formatRessourceDocumentaire.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("FormatRessourceDocumentaire total count : {}", count);
        return count;
    }
}
