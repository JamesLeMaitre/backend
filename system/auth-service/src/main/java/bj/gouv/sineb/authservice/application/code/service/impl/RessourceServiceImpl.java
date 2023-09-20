package bj.gouv.sineb.authservice.application.code.service.impl;

import bj.gouv.sineb.authservice.application.code.entity.Ressource;
import bj.gouv.sineb.authservice.application.code.dto.request.RessourceRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.RessourceResponse;
import bj.gouv.sineb.authservice.application.code.mapper.RessourceMapper;
import bj.gouv.sineb.authservice.application.code.repository.RessourceRepository;
import bj.gouv.sineb.authservice.application.code.service.RessourceService;
import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Slf4j
@Service
public class RessourceServiceImpl implements RessourceService {

    private final  RessourceRepository repository;
    private final RessourceMapper ressourceMapper;

    public RessourceServiceImpl(RessourceRepository repository, RessourceMapper ressourceMapper) {
        this.repository = repository;
        this.ressourceMapper = ressourceMapper;
    }


    @Override
    @Transactional
    public RessourceResponse save(RessourceRequest request) {
        log.info("Saving Ressource with payload: "+request.toString());
        Ressource ressource = ressourceMapper.toEntity(request);
        RessourceResponse ressourceResponse = ressourceMapper.toDto(repository.save(ressource));
        log.info("Ressource is created with id: {}", ressource.getId());
        log.info("Returning RessourceResponse with trackingId: {}", ressourceResponse.getTrackingId());
        return ressourceResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public RessourceResponse getOne(UUID id) {
        log.info("Searching for Ressource with id: "+id);
        Ressource ressource = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("Ressource with id: "+id+" not found!"));
        RessourceResponse ressourceResponse = ressourceMapper.toDto(ressource);
        log.info("Ressource is found with id: {}", ressource.getId());
        log.info("Returning RessourceResponse with trackingId: {}", ressourceResponse.getTrackingId());
        return ressourceResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public RessourceResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for Ressource with trackingId: "+trackingId);
        Ressource ressource = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Ressource with trackingId: "+trackingId+" not found!"));
        RessourceResponse ressourceResponse = ressourceMapper.toDto(ressource);
        log.info("Ressource is found with trackingId: {}", ressource.getTrackingId());
        log.info("Returning RessourceResponse with trackingId: {}", ressourceResponse.getTrackingId());
        return ressourceResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RessourceResponse> getAll(int page, int size, boolean deleted) {
        log.info("Searching for Ressource list with (page, size, deleted) : "+"("+page+", "+size+", "+deleted+")");
        Page<Ressource> ressourcePage = repository.findAllByDeleted(deleted, PageRequest.of(page, size));
        Page<RessourceResponse> ressourcePageResponse = ressourcePage.map(ressourceMapper::toDto);
        log.info("Ressource list found with size: {}", ressourcePage.getTotalElements());
        log.info("Returning RessourceResponse list with size: {}", ressourcePage.getTotalElements());
        return ressourcePageResponse;
    }

    @Override
    @Transactional
    public RessourceResponse updateOne(RessourceRequest request) {
        log.info("Updating Ressource with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("Ressource trackingId should not be null !");

        Ressource ressource = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Ressource with id: "+request.getTrackingId()+" not found!"));
        ressource.update(request);
        RessourceResponse ressourceResponse = ressourceMapper.toDto(repository.save(ressource));
        log.info("Ressource is updated with id: {}", ressource.getId());
        log.info("Returning RessourceResponse with trackingId: {}", ressourceResponse.getTrackingId());
        return ressourceResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting Ressource with trackingId:: "+trackingId);
        Ressource ressource = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Ressource with id: "+trackingId+" not found!"));
        ressource.delete();
        repository.save(ressource);
        log.info("Ressource is deleted with id: {}", ressource.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countByDeletedFalse();
        log.info("Ressource total count : {}", count);
        return count;
    }

}
