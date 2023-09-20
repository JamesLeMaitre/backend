package bj.gouv.sineb.ddbservice.application.code.service.aae.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.RoleActeurRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.RoleActeurResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.RoleActeur;
import bj.gouv.sineb.ddbservice.application.code.mapper.aae.RoleActeurMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.aae.RoleActeurRepository;
import bj.gouv.sineb.ddbservice.application.code.service.aae.RoleActeurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class RoleActeurServiceImpl implements RoleActeurService {
    private final RoleActeurRepository repository;
    private final RoleActeurMapper roleActeurMapper;

    public RoleActeurServiceImpl(RoleActeurRepository repository, RoleActeurMapper roleActeurMapper) {
        this.repository = repository;
        this.roleActeurMapper = roleActeurMapper;
    }


    @Override
    @Transactional
    public RoleActeurResponse save(RoleActeurRequest request) {
        log.info("Saving RoleActeur with payload: "+request.toString());
        RoleActeur roleActeur = roleActeurMapper.toEntity(request);
        roleActeur.setDataStatus(DataStatus.CREATED);
        RoleActeurResponse roleActeurResponse = roleActeurMapper.toDto(repository.save(roleActeur));
        log.info("RoleActeur is created with id: {}", roleActeur.getId());
        log.info("Returning RoleActeurResponse with trackingId: {}", roleActeurResponse.getTrackingId());
        return roleActeurResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public RoleActeurResponse getOne(UUID id) {
        log.info("Searching for RoleActeur with id: "+id);
        RoleActeur roleActeur = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("RoleActeur with id: "+id+" not found!"));
        RoleActeurResponse roleActeurResponse = roleActeurMapper.toDto(roleActeur);
        log.info("RoleActeur is found with id: {}", roleActeur.getId());
        log.info("Returning RoleActeurResponse with trackingId: {}", roleActeurResponse.getTrackingId());
        return roleActeurResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public RoleActeurResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for RoleActeur with trackingId: "+trackingId);
        RoleActeur roleActeur = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("RoleActeur with trackingId: "+trackingId+" not found!"));
        RoleActeurResponse roleActeurResponse = roleActeurMapper.toDto(roleActeur);
        log.info("RoleActeur is found with trackingId: {}", roleActeur.getTrackingId());
        log.info("Returning RoleActeurResponse with trackingId: {}", roleActeurResponse.getTrackingId());
        return roleActeurResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleActeurResponse> getAll(int page, int size) {
        log.info("Searching for RoleActeur list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<RoleActeur> roleActeurPage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<RoleActeurResponse> roleActeurPageResponse = roleActeurPage.map(roleActeurMapper::toDto);
        log.info("RoleActeur list found with size: {}", roleActeurPage.getTotalElements());
        log.info("Returning RoleActeurResponse list with size: {}", roleActeurPage.getTotalElements());
        return roleActeurPageResponse;
    }

    @Override
    @Transactional
    public RoleActeurResponse updateOne(RoleActeurRequest request) {
        log.info("Updating RoleActeur with payload: "+request.toString());
        if (request.getTrackingId()==null)
            throw new CustomException("RoleActeur trackingId should not be null !");

        RoleActeur roleActeur = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("RoleActeur with id: "+request.getTrackingId()+" not found!"));
        roleActeur.setDataStatus(DataStatus.UPDATED);
        RoleActeurResponse roleActeurResponse = roleActeurMapper.toDto(repository.save(roleActeur));
        log.info("RoleActeur is updated with id: {}", roleActeur.getId());
        log.info("Returning RoleActeurResponse with trackingId: {}", roleActeurResponse.getTrackingId());
        return roleActeurResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting RoleActeur with trackingId:: "+trackingId);
        RoleActeur roleActeur = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("RoleActeur with id: "+trackingId+" not found!"));
        roleActeur.setDataStatus(DataStatus.DELETED);
        repository.save(roleActeur);
        log.info("RoleActeur is deleted with id: {}", roleActeur.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("RoleActeur total count : {}", count);
        return count;
    }
}
