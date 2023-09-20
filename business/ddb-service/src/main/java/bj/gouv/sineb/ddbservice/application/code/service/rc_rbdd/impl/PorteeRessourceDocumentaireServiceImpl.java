package bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.rc_rbdd.PorteeRessourceDocumentaireRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.rc_rbdd.PorteeRessourceDocumentaireResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.PorteeRessourceDocumentaire;
import bj.gouv.sineb.ddbservice.application.code.repository.rc_rbdd.PorteeRessourceDocumentaireRepository;
import bj.gouv.sineb.ddbservice.application.code.mapper.rc_rbdd.PorteeRessourceDocumentaireMapper;
import bj.gouv.sineb.ddbservice.application.code.service.rc_rbdd.PorteeRessourceDocumentaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class PorteeRessourceDocumentaireServiceImpl implements PorteeRessourceDocumentaireService {
    private final PorteeRessourceDocumentaireRepository repository;
    private final PorteeRessourceDocumentaireMapper porteeRessourceDocumentaireMapper;

    public PorteeRessourceDocumentaireServiceImpl(PorteeRessourceDocumentaireRepository repository, PorteeRessourceDocumentaireMapper porteeRessourceDocumentaireMapper) {
        this.repository = repository;
        this.porteeRessourceDocumentaireMapper = porteeRessourceDocumentaireMapper;
    }


    @Override
    @Transactional
    public PorteeRessourceDocumentaireResponse save(PorteeRessourceDocumentaireRequest request) {
        log.info("Saving PorteeRessourceDocumentaire with payload: "+request.toString());
        PorteeRessourceDocumentaire porteeRessourceDocumentaire = porteeRessourceDocumentaireMapper.toEntity(request);
        porteeRessourceDocumentaire.setDataStatus(DataStatus.CREATED);
        PorteeRessourceDocumentaireResponse porteeRessourceDocumentaireResponse = porteeRessourceDocumentaireMapper.toDto(repository.save(porteeRessourceDocumentaire));
        log.info("PorteeRessourceDocumentaire is created with id: {}", porteeRessourceDocumentaire.getId());
        log.info("Returning PorteeRessourceDocumentaireResponse with trackingId: {}", porteeRessourceDocumentaireResponse.getTrackingId());
        return porteeRessourceDocumentaireResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public PorteeRessourceDocumentaireResponse getOne(UUID id) {
        log.info("Searching for PorteeRessourceDocumentaire with id: "+id);
        PorteeRessourceDocumentaire porteeRessourceDocumentaire = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("PorteeRessourceDocumentaire with id: "+id+" not found!"));
        PorteeRessourceDocumentaireResponse porteeRessourceDocumentaireResponse = porteeRessourceDocumentaireMapper.toDto(porteeRessourceDocumentaire);
        log.info("PorteeRessourceDocumentaire is found with id: {}", porteeRessourceDocumentaire.getId());
        log.info("Returning PorteeRessourceDocumentaireResponse with trackingId: {}", porteeRessourceDocumentaireResponse.getTrackingId());
        return porteeRessourceDocumentaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public PorteeRessourceDocumentaireResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for PorteeRessourceDocumentaire with trackingId: "+trackingId);
        PorteeRessourceDocumentaire porteeRessourceDocumentaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("PorteeRessourceDocumentaire with trackingId: "+trackingId+" not found!"));
        PorteeRessourceDocumentaireResponse porteeRessourceDocumentaireResponse = porteeRessourceDocumentaireMapper.toDto(porteeRessourceDocumentaire);
        log.info("PorteeRessourceDocumentaire is found with trackingId: {}", porteeRessourceDocumentaire.getTrackingId());
        log.info("Returning PorteeRessourceDocumentaireResponse with trackingId: {}", porteeRessourceDocumentaireResponse.getTrackingId());
        return porteeRessourceDocumentaireResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PorteeRessourceDocumentaireResponse> getAll(int page, int size) {
        log.info("Searching for PorteeRessourceDocumentaire list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<PorteeRessourceDocumentaire> porteeRessourceDocumentairePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<PorteeRessourceDocumentaireResponse> porteeRessourceDocumentairePageResponse = porteeRessourceDocumentairePage.map(porteeRessourceDocumentaireMapper::toDto);
        log.info("PorteeRessourceDocumentaire list found with size: {}", porteeRessourceDocumentairePage.getTotalElements());
        log.info("Returning PorteeRessourceDocumentaireResponse list with size: {}", porteeRessourceDocumentairePage.getTotalElements());
        return porteeRessourceDocumentairePageResponse;
    }

    @Override
    @Transactional
    public PorteeRessourceDocumentaireResponse updateOne(PorteeRessourceDocumentaireRequest request) {
        log.info("Updating PorteeRessourceDocumentaire with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("PorteeRessourceDocumentaire trackingId should not be null !");

        PorteeRessourceDocumentaire porteeRessourceDocumentaire = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("PorteeRessourceDocumentaire with id: "+request.getTrackingId()+" not found!"));
        porteeRessourceDocumentaire.setDataStatus(DataStatus.UPDATED);
        PorteeRessourceDocumentaireResponse porteeRessourceDocumentaireResponse = porteeRessourceDocumentaireMapper.toDto(repository.save(porteeRessourceDocumentaire));
        log.info("PorteeRessourceDocumentaire is updated with id: {}", porteeRessourceDocumentaire.getId());
        log.info("Returning PorteeRessourceDocumentaireResponse with trackingId: {}", porteeRessourceDocumentaireResponse.getTrackingId());
        return porteeRessourceDocumentaireResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting PorteeRessourceDocumentaire with trackingId:: "+trackingId);
        PorteeRessourceDocumentaire porteeRessourceDocumentaire = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("PorteeRessourceDocumentaire with id: "+trackingId+" not found!"));
        porteeRessourceDocumentaire.setDataStatus(DataStatus.DELETED);
        repository.save(porteeRessourceDocumentaire);
        log.info("PorteeRessourceDocumentaire is deleted with id: {}", porteeRessourceDocumentaire.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("PorteeRessourceDocumentaire total count : {}", count);
        return count;
    }
}
