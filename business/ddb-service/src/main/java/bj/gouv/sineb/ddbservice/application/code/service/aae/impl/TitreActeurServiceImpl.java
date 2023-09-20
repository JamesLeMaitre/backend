package bj.gouv.sineb.ddbservice.application.code.service.aae.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.TitreActeurRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.TitreActeurResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.TitreActeur;
import bj.gouv.sineb.ddbservice.application.code.mapper.aae.TitreActeurMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.aae.TitreActeurRepository;
import bj.gouv.sineb.ddbservice.application.code.service.aae.TitreActeurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class TitreActeurServiceImpl implements TitreActeurService {
    private final TitreActeurRepository repository;
    private final TitreActeurMapper titreActeurMapper;

    public TitreActeurServiceImpl(TitreActeurRepository repository, TitreActeurMapper titreActeurMapper) {
        this.repository = repository;
        this.titreActeurMapper = titreActeurMapper;
    }


    @Override
    @Transactional
    public TitreActeurResponse save(TitreActeurRequest request) {
        log.info("Saving TitreActeur with payload: "+request.toString());
        TitreActeur titreActeur = titreActeurMapper.toEntity(request);
        titreActeur.setDataStatus(DataStatus.CREATED);
        TitreActeurResponse titreActeurResponse = titreActeurMapper.toDto(repository.save(titreActeur));
        log.info("TitreActeur is created with id: {}", titreActeur.getId());
        log.info("Returning TitreActeurResponse with trackingId: {}", titreActeurResponse.getTrackingId());
        return titreActeurResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public TitreActeurResponse getOne(UUID id) {
        log.info("Searching for TitreActeur with id: "+id);
        TitreActeur titreActeur = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("TitreActeur with id: "+id+" not found!"));
        TitreActeurResponse titreActeurResponse = titreActeurMapper.toDto(titreActeur);
        log.info("TitreActeur is found with id: {}", titreActeur.getId());
        log.info("Returning TitreActeurResponse with trackingId: {}", titreActeurResponse.getTrackingId());
        return titreActeurResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public TitreActeurResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for TitreActeur with trackingId: "+trackingId);
        TitreActeur titreActeur = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("TitreActeur with trackingId: "+trackingId+" not found!"));
        TitreActeurResponse titreActeurResponse = titreActeurMapper.toDto(titreActeur);
        log.info("TitreActeur is found with trackingId: {}", titreActeur.getTrackingId());
        log.info("Returning TitreActeurResponse with trackingId: {}", titreActeurResponse.getTrackingId());
        return titreActeurResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TitreActeurResponse> getAll(int page, int size) {
        log.info("Searching for TitreActeur list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<TitreActeur> titreActeurPage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<TitreActeurResponse> titreActeurPageResponse = titreActeurPage.map(titreActeurMapper::toDto);
        log.info("TitreActeur list found with size: {}", titreActeurPage.getTotalElements());
        log.info("Returning TitreActeurResponse list with size: {}", titreActeurPage.getTotalElements());
        return titreActeurPageResponse;
    }

    @Override
    @Transactional
    public TitreActeurResponse updateOne(TitreActeurRequest request) {
        log.info("Updating TitreActeur with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("TitreActeur trackingId should not be null !");

        TitreActeur titreActeur = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("TitreActeur with id: "+request.getTrackingId()+" not found!"));
        titreActeur.setDataStatus(DataStatus.UPDATED);
        TitreActeurResponse titreActeurResponse = titreActeurMapper.toDto(repository.save(titreActeur));
        log.info("TitreActeur is updated with id: {}", titreActeur.getId());
        log.info("Returning TitreActeurResponse with trackingId: {}", titreActeurResponse.getTrackingId());
        return titreActeurResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting TitreActeur with trackingId:: "+trackingId);
        TitreActeur titreActeur = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("TitreActeur with id: "+trackingId+" not found!"));
        titreActeur.setDataStatus(DataStatus.DELETED);
        repository.save(titreActeur);
        log.info("TitreActeur is deleted with id: {}", titreActeur.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("TitreActeur total count : {}", count);
        return count;
    }
}
