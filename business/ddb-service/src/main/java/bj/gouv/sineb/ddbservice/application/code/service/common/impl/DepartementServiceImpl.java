package bj.gouv.sineb.ddbservice.application.code.service.common.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.DepartementRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.DepartementResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Departement;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.DepartementMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.DepartementRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.DepartementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class DepartementServiceImpl implements DepartementService {
    private final DepartementRepository repository;
    private final DepartementMapper departementMapper;

    public DepartementServiceImpl(DepartementRepository repository, DepartementMapper departementMapper) {
        this.repository = repository;
        this.departementMapper = departementMapper;
    }


    @Override
    @Transactional
    public DepartementResponse save(DepartementRequest request) {
        log.info("Saving Departement with payload: "+request.toString());
        Departement departement = departementMapper.toEntity(request);
        departement.setDataStatus(DataStatus.CREATED);
        DepartementResponse departementResponse = departementMapper.toDto(repository.save(departement));
        log.info("Departement is created with id: {}", departement.getId());
        log.info("Returning DepartementResponse with trackingId: {}", departementResponse.getTrackingId());
        return departementResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public DepartementResponse getOne(UUID id) {
        log.info("Searching for Departement with id: "+id);
        Departement departement = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("Departement with id: "+id+" not found!"));
        DepartementResponse departementResponse = departementMapper.toDto(departement);
        log.info("Departement is found with id: {}", departement.getId());
        log.info("Returning DepartementResponse with trackingId: {}", departementResponse.getTrackingId());
        return departementResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public DepartementResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for Departement with trackingId: "+trackingId);
        Departement departement = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Departement with trackingId: "+trackingId+" not found!"));
        DepartementResponse departementResponse = departementMapper.toDto(departement);
        log.info("Departement is found with trackingId: {}", departement.getTrackingId());
        log.info("Returning DepartementResponse with trackingId: {}", departementResponse.getTrackingId());
        return departementResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DepartementResponse> getAll(int page, int size) {
        log.info("Searching for Departement list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<Departement> departementPage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<DepartementResponse> departementPageResponse = departementPage.map(departementMapper::toDto);
        log.info("Departement list found with size: {}", departementPage.getTotalElements());
        log.info("Returning DepartementResponse list with size: {}", departementPage.getTotalElements());
        return departementPageResponse;
    }

    @Override
    @Transactional
    public DepartementResponse updateOne(DepartementRequest request) {
        log.info("Updating Departement with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("Departement trackingId should not be null !");

        Departement departement = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Departement with id: "+request.getTrackingId()+" not found!"));
        departement.setDataStatus(DataStatus.UPDATED);
        DepartementResponse departementResponse = departementMapper.toDto(repository.save(departement));
        log.info("Departement is updated with id: {}", departement.getId());
        log.info("Returning DepartementResponse with trackingId: {}", departementResponse.getTrackingId());
        return departementResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting Departement with trackingId:: "+trackingId);
        Departement departement = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Departement with id: "+trackingId+" not found!"));
        departement.setDataStatus(DataStatus.DELETED);
        repository.save(departement);
        log.info("Departement is deleted with id: {}", departement.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("Departement total count : {}", count);
        return count;
    }
}
