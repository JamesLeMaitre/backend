package bj.gouv.sineb.ddbservice.application.code.service.aae.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.MotifQualificationRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.MotifQualificationResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.MotifQualification;
import bj.gouv.sineb.ddbservice.application.code.mapper.aae.MotifQualificationMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.aae.MotifQualificationRepository;
import bj.gouv.sineb.ddbservice.application.code.service.aae.MotifQualificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class MotifQualificationServiceImpl implements MotifQualificationService {
    private final MotifQualificationRepository repository;
    private final MotifQualificationMapper motifQualificationMapper;

    public MotifQualificationServiceImpl(MotifQualificationRepository repository, MotifQualificationMapper motifQualificationMapper) {
        this.repository = repository;
        this.motifQualificationMapper = motifQualificationMapper;
    }


    @Override
    @Transactional
    public MotifQualificationResponse save(MotifQualificationRequest request) {
        log.info("Saving MotifQualification with payload: "+request.toString());
        MotifQualification motifQualification = motifQualificationMapper.toEntity(request);
        motifQualification.setDataStatus(DataStatus.CREATED);
        MotifQualificationResponse motifQualificationResponse = motifQualificationMapper.toDto(repository.save(motifQualification));
        log.info("MotifQualification is created with id: {}", motifQualification.getId());
        log.info("Returning MotifQualificationResponse with trackingId: {}", motifQualificationResponse.getTrackingId());
        return motifQualificationResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public MotifQualificationResponse getOne(UUID id) {
        log.info("Searching for MotifQualification with id: "+id);
        MotifQualification motifQualification = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("MotifQualification with id: "+id+" not found!"));
        MotifQualificationResponse motifQualificationResponse = motifQualificationMapper.toDto(motifQualification);
        log.info("MotifQualification is found with id: {}", motifQualification.getId());
        log.info("Returning MotifQualificationResponse with trackingId: {}", motifQualificationResponse.getTrackingId());
        return motifQualificationResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public MotifQualificationResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for MotifQualification with trackingId: "+trackingId);
        MotifQualification motifQualification = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("MotifQualification with trackingId: "+trackingId+" not found!"));
        MotifQualificationResponse motifQualificationResponse = motifQualificationMapper.toDto(motifQualification);
        log.info("MotifQualification is found with trackingId: {}", motifQualification.getTrackingId());
        log.info("Returning MotifQualificationResponse with trackingId: {}", motifQualificationResponse.getTrackingId());
        return motifQualificationResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MotifQualificationResponse> getAll(int page, int size) {
        log.info("Searching for MotifQualification list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<MotifQualification> motifQualificationPage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<MotifQualificationResponse> motifQualificationPageResponse = motifQualificationPage.map(motifQualificationMapper::toDto);
        log.info("MotifQualification list found with size: {}", motifQualificationPage.getTotalElements());
        log.info("Returning MotifQualificationResponse list with size: {}", motifQualificationPage.getTotalElements());
        return motifQualificationPageResponse;
    }

    @Override
    @Transactional
    public MotifQualificationResponse updateOne(MotifQualificationRequest request) {
        log.info("Updating MotifQualification with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("MotifQualification trackingId should not be null !");

        MotifQualification motifQualification = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("MotifQualification with id: "+request.getTrackingId()+" not found!"));
        motifQualification.setDataStatus(DataStatus.UPDATED);
        MotifQualificationResponse motifQualificationResponse = motifQualificationMapper.toDto(repository.save(motifQualification));
        log.info("MotifQualification is updated with id: {}", motifQualification.getId());
        log.info("Returning MotifQualificationResponse with trackingId: {}", motifQualificationResponse.getTrackingId());
        return motifQualificationResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting MotifQualification with trackingId:: "+trackingId);
        MotifQualification motifQualification = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("MotifQualification with id: "+trackingId+" not found!"));
        motifQualification.setDataStatus(DataStatus.DELETED);
        repository.save(motifQualification);
        log.info("MotifQualification is deleted with id: {}", motifQualification.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("MotifQualification total count : {}", count);
        return count;
    }
}
