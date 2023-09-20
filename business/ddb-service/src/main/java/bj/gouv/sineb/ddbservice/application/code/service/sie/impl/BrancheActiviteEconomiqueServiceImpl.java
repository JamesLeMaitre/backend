package bj.gouv.sineb.ddbservice.application.code.service.sie.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.sie.BrancheActiviteEconomiqueRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.sie.BrancheActiviteEconomiqueResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.sie.BrancheActiviteEconomique;
import bj.gouv.sineb.ddbservice.application.code.mapper.sie.BrancheActiviteEconomiqueMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.sie.BrancheActiviteEconomiqueRepository;
import bj.gouv.sineb.ddbservice.application.code.service.sie.BrancheActiviteEconomiqueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class BrancheActiviteEconomiqueServiceImpl implements BrancheActiviteEconomiqueService {
    private final BrancheActiviteEconomiqueRepository repository;
    private final BrancheActiviteEconomiqueMapper brancheActiviteEconomiqueMapper;

    public BrancheActiviteEconomiqueServiceImpl(BrancheActiviteEconomiqueRepository repository, BrancheActiviteEconomiqueMapper brancheActiviteEconomiqueMapper) {
        this.repository = repository;
        this.brancheActiviteEconomiqueMapper = brancheActiviteEconomiqueMapper;
    }


    @Override
    @Transactional
    public BrancheActiviteEconomiqueResponse save(BrancheActiviteEconomiqueRequest request) {
        log.info("Saving BrancheActiviteEconomique with payload: "+request.toString());
        BrancheActiviteEconomique brancheActiviteEconomique = brancheActiviteEconomiqueMapper.toEntity(request);
        brancheActiviteEconomique.setDataStatus(DataStatus.CREATED);
        BrancheActiviteEconomiqueResponse brancheActiviteEconomiqueResponse = brancheActiviteEconomiqueMapper.toDto(repository.save(brancheActiviteEconomique));
        log.info("BrancheActiviteEconomique is created with id: {}", brancheActiviteEconomique.getId());
        log.info("Returning BrancheActiviteEconomiqueResponse with trackingId: {}", brancheActiviteEconomiqueResponse.getTrackingId());
        return brancheActiviteEconomiqueResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public BrancheActiviteEconomiqueResponse getOne(UUID id) {
        log.info("Searching for BrancheActiviteEconomique with id: "+id);
        BrancheActiviteEconomique brancheActiviteEconomique = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("BrancheActiviteEconomique with id: "+id+" not found!"));
        BrancheActiviteEconomiqueResponse brancheActiviteEconomiqueResponse = brancheActiviteEconomiqueMapper.toDto(brancheActiviteEconomique);
        log.info("BrancheActiviteEconomique is found with id: {}", brancheActiviteEconomique.getId());
        log.info("Returning BrancheActiviteEconomiqueResponse with trackingId: {}", brancheActiviteEconomiqueResponse.getTrackingId());
        return brancheActiviteEconomiqueResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public BrancheActiviteEconomiqueResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for BrancheActiviteEconomique with trackingId: "+trackingId);
        BrancheActiviteEconomique brancheActiviteEconomique = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("BrancheActiviteEconomique with trackingId: "+trackingId+" not found!"));
        BrancheActiviteEconomiqueResponse brancheActiviteEconomiqueResponse = brancheActiviteEconomiqueMapper.toDto(brancheActiviteEconomique);
        log.info("BrancheActiviteEconomique is found with trackingId: {}", brancheActiviteEconomique.getTrackingId());
        log.info("Returning BrancheActiviteEconomiqueResponse with trackingId: {}", brancheActiviteEconomiqueResponse.getTrackingId());
        return brancheActiviteEconomiqueResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BrancheActiviteEconomiqueResponse> getAll(int page, int size) {
        log.info("Searching for BrancheActiviteEconomique list with (page, size, deleted) : "+"("+page+", "+size+", "+ DataStatus.DELETED +")");
        Page<BrancheActiviteEconomique> brancheActiviteEconomiquePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<BrancheActiviteEconomiqueResponse> brancheActiviteEconomiquePageResponse = brancheActiviteEconomiquePage.map(brancheActiviteEconomiqueMapper::toDto);
        log.info("BrancheActiviteEconomique list found with size: {}", brancheActiviteEconomiquePage.getTotalElements());
        log.info("Returning BrancheActiviteEconomiqueResponse list with size: {}", brancheActiviteEconomiquePage.getTotalElements());
        return brancheActiviteEconomiquePageResponse;
    }

    @Override
    public Page<BrancheActiviteEconomiqueResponse> getAllByBlocActiviteEconomiqueId(UUID flocActiviteEconomiqueTrackingId, int page, int size) {
        log.info("Searching for BrancheActiviteEconomique list by blocActiviteEconomique with (flocActiviteEconomiqueTrackingId, page, size, deleted) : "+"("+flocActiviteEconomiqueTrackingId+", "+page+", "+size+", "+DataStatus.DELETED+")");
        Page<BrancheActiviteEconomique> brancheActiviteEconomiquePage = repository.findAllByDataStatusIsNotAndBlocActiviteEconomiqueTrackingId(DataStatus.DELETED, flocActiviteEconomiqueTrackingId, PageRequest.of(page, size));
        Page<BrancheActiviteEconomiqueResponse> brancheActiviteEconomiquePageResponse = brancheActiviteEconomiquePage.map(brancheActiviteEconomiqueMapper::toDto);
        log.info("BrancheActiviteEconomique list found with size: {}", brancheActiviteEconomiquePage.getTotalElements());
        log.info("Returning BrancheActiviteEconomiqueResponse list with size: {}", brancheActiviteEconomiquePage.getTotalElements());
        return brancheActiviteEconomiquePageResponse;
    }

    @Override
    @Transactional
    public BrancheActiviteEconomiqueResponse updateOne(BrancheActiviteEconomiqueRequest request) {
        log.info("Updating BrancheActiviteEconomique with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("BrancheActiviteEconomique trackingId should not be null !");

        BrancheActiviteEconomique brancheActiviteEconomique = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("BrancheActiviteEconomique with id: "+request.getTrackingId()+" not found!"));
        brancheActiviteEconomique.setDataStatus(DataStatus.UPDATED);
        BrancheActiviteEconomiqueResponse brancheActiviteEconomiqueResponse = brancheActiviteEconomiqueMapper.toDto(repository.save(brancheActiviteEconomique));
        log.info("BrancheActiviteEconomique is updated with id: {}", brancheActiviteEconomique.getId());
        log.info("Returning BrancheActiviteEconomiqueResponse with trackingId: {}", brancheActiviteEconomiqueResponse.getTrackingId());
        return brancheActiviteEconomiqueResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting BrancheActiviteEconomique with trackingId:: "+trackingId);
        BrancheActiviteEconomique brancheActiviteEconomique = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("BrancheActiviteEconomique with id: "+trackingId+" not found!"));
        brancheActiviteEconomique.setDataStatus(DataStatus.DELETED);
        repository.save(brancheActiviteEconomique);
        log.info("BrancheActiviteEconomique is deleted with id: {}", brancheActiviteEconomique.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("BrancheActiviteEconomique total count : {}", count);
        return count;
    }
}
