package bj.gouv.sineb.ddbservice.application.code.service.aae.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.ProfessionRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.ProfessionResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.Profession;
import bj.gouv.sineb.ddbservice.application.code.mapper.aae.ProfessionMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.aae.ProfessionRepository;
import bj.gouv.sineb.ddbservice.application.code.service.aae.ProfessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class ProfessionServiceImpl implements ProfessionService {
    private final ProfessionRepository repository;
    private final ProfessionMapper professionMapper;

    public ProfessionServiceImpl(ProfessionRepository repository, ProfessionMapper professionMapper) {
        this.repository = repository;
        this.professionMapper = professionMapper;
    }


    @Override
    @Transactional
    public ProfessionResponse save(ProfessionRequest request) {
        log.info("Saving Profession with payload: "+request.toString());
        Profession profession = professionMapper.toEntity(request);
        profession.setDataStatus(DataStatus.CREATED);
        ProfessionResponse professionResponse = professionMapper.toDto(repository.save(profession));
        log.info("Profession is created with id: {}", profession.getId());
        log.info("Returning ProfessionResponse with trackingId: {}", professionResponse.getTrackingId());
        return professionResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public ProfessionResponse getOne(UUID id) {
        log.info("Searching for Profession with id: "+id);
        Profession profession = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("Profession with id: "+id+" not found!"));
        ProfessionResponse professionResponse = professionMapper.toDto(profession);
        log.info("Profession is found with id: {}", profession.getId());
        log.info("Returning ProfessionResponse with trackingId: {}", professionResponse.getTrackingId());
        return professionResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public ProfessionResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for Profession with trackingId: "+trackingId);
        Profession profession = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Profession with trackingId: "+trackingId+" not found!"));
        ProfessionResponse professionResponse = professionMapper.toDto(profession);
        log.info("Profession is found with trackingId: {}", profession.getTrackingId());
        log.info("Returning ProfessionResponse with trackingId: {}", professionResponse.getTrackingId());
        return professionResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProfessionResponse> getAll(int page, int size) {
        log.info("Searching for Profession list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<Profession> professionPage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<ProfessionResponse> professionPageResponse = professionPage.map(professionMapper::toDto);
        log.info("Profession list found with size: {}", professionPage.getTotalElements());
        log.info("Returning ProfessionResponse list with size: {}", professionPage.getTotalElements());
        return professionPageResponse;
    }

    @Override
    @Transactional
    public ProfessionResponse updateOne(ProfessionRequest request) {
        log.info("Updating Profession with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("Profession trackingId should not be null !");

        Profession profession = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Profession with id: "+request.getTrackingId()+" not found!"));
        profession.setDataStatus(DataStatus.UPDATED);
        ProfessionResponse professionResponse = professionMapper.toDto(repository.save(profession));
        log.info("Profession is updated with id: {}", profession.getId());
        log.info("Returning ProfessionResponse with trackingId: {}", professionResponse.getTrackingId());
        return professionResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting Profession with trackingId:: "+trackingId);
        Profession profession = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Profession with id: "+trackingId+" not found!"));
        profession.setDataStatus(DataStatus.DELETED);
        repository.save(profession);
        log.info("Profession is deleted with id: {}", profession.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("Profession total count : {}", count);
        return count;
    }
}
