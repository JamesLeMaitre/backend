package bj.gouv.sineb.ddbservice.application.code.service.common.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.VilleRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.VilleResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Ville;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.VilleMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.VilleRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.VilleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class VilleServiceImpl implements VilleService {
    private final VilleRepository repository;
    private final VilleMapper civiliteMapper;

    public VilleServiceImpl(VilleRepository repository, VilleMapper civiliteMapper) {
        this.repository = repository;
        this.civiliteMapper = civiliteMapper;
    }


    @Override
    @Transactional
    public VilleResponse save(VilleRequest request) {
        log.info("Saving Ville with payload: "+request.toString());
        Ville ville = civiliteMapper.toEntity(request);
        ville.setDataStatus(DataStatus.CREATED);
        VilleResponse villeResponse = civiliteMapper.toDto(repository.save(ville));
        log.info("Ville is created with id: {}", ville.getId());
        log.info("Returning VilleResponse with trackingId: {}", villeResponse.getTrackingId());
        return villeResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public VilleResponse getOne(UUID id) {
        log.info("Searching for Ville with id: "+id);
        Ville civilite = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("Ville with id: "+id+" not found!"));
        VilleResponse civiliteResponse = civiliteMapper.toDto(civilite);
        log.info("Ville is found with id: {}", civilite.getId());
        log.info("Returning VilleResponse with trackingId: {}", civiliteResponse.getTrackingId());
        return civiliteResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public VilleResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for Ville with trackingId: "+trackingId);
        Ville ville = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Ville with trackingId: "+trackingId+" not found!"));
        VilleResponse villeResponse = civiliteMapper.toDto(ville);
        log.info("Ville is found with trackingId: {}", ville.getTrackingId());
        log.info("Returning VilleResponse with trackingId: {}", villeResponse.getTrackingId());
        return villeResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VilleResponse> getAll(int page, int size) {
        log.info("Searching for Ville list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<Ville> civilitePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<VilleResponse> civilitePageResponse = civilitePage.map(civiliteMapper::toDto);
        log.info("Ville list found with size: {}", civilitePage.getTotalElements());
        log.info("Returning VilleResponse list with size: {}", civilitePage.getTotalElements());
        return civilitePageResponse;
    }

    @Override
    @Transactional
    public VilleResponse updateOne(VilleRequest request) {
        log.info("Updating Ville with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("Ville trackingId should not be null !");

        Ville civilite = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Ville with id: "+request.getTrackingId()+" not found!"));
        VilleResponse civiliteResponse = civiliteMapper.toDto(repository.save(civilite));
        log.info("Ville is updated with id: {}", civilite.getId());
        log.info("Returning VilleResponse with trackingId: {}", civiliteResponse.getTrackingId());
        return civiliteResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting Ville with trackingId:: "+trackingId);
        Ville ville = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Ville with id: "+trackingId+" not found!"));
        ville.setDataStatus(DataStatus.DELETED);
        repository.save(ville);
        log.info("Ville is deleted with id: {}", ville.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("Ville total count : {}", count);
        return count;
    }
}
