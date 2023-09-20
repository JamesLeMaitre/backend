package bj.gouv.sineb.ddbservice.application.code.service.common.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.ModeReceptionRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.ModeReceptionResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.ModeReception;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.ModeReceptionMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.ModeReceptionRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.ModeReceptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class ModeReceptionServiceImpl implements ModeReceptionService {
    private final ModeReceptionRepository repository;
    private final ModeReceptionMapper modeReceptionMapper;

    public ModeReceptionServiceImpl(ModeReceptionRepository repository, ModeReceptionMapper modeReceptionMapper) {
        this.repository = repository;
        this.modeReceptionMapper = modeReceptionMapper;
    }


    @Override
    @Transactional
    public ModeReceptionResponse save(ModeReceptionRequest request) {
        log.info("Saving ModeReception with payload: "+request.toString());
        ModeReception modeReception = modeReceptionMapper.toEntity(request);
        modeReception.setDataStatus(DataStatus.CREATED);
        ModeReceptionResponse modeReceptionResponse = modeReceptionMapper.toDto(repository.save(modeReception));
        log.info("ModeReception is created with id: {}", modeReception.getId());
        log.info("Returning ModeReceptionResponse with trackingId: {}", modeReceptionResponse.getTrackingId());
        return modeReceptionResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public ModeReceptionResponse getOne(UUID id) {
        log.info("Searching for ModeReception with id: "+id);
        ModeReception modeReception = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("ModeReception with id: "+id+" not found!"));
        ModeReceptionResponse modeReceptionResponse = modeReceptionMapper.toDto(modeReception);
        log.info("ModeReception is found with id: {}", modeReception.getId());
        log.info("Returning ModeReceptionResponse with trackingId: {}", modeReceptionResponse.getTrackingId());
        return modeReceptionResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public ModeReceptionResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for ModeReception with trackingId: "+trackingId);
        ModeReception modeReception = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("ModeReception with trackingId: "+trackingId+" not found!"));
        ModeReceptionResponse modeReceptionResponse = modeReceptionMapper.toDto(modeReception);
        log.info("ModeReception is found with trackingId: {}", modeReception.getTrackingId());
        log.info("Returning ModeReceptionResponse with trackingId: {}", modeReceptionResponse.getTrackingId());
        return modeReceptionResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModeReceptionResponse> getAll(int page, int size) {
        log.info("Searching for ModeReception list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<ModeReception> modeReceptionPage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<ModeReceptionResponse> modeReceptionPageResponse = modeReceptionPage.map(modeReceptionMapper::toDto);
        log.info("ModeReception list found with size: {}", modeReceptionPage.getTotalElements());
        log.info("Returning ModeReceptionResponse list with size: {}", modeReceptionPage.getTotalElements());
        return modeReceptionPageResponse;
    }

    @Override
    @Transactional
    public ModeReceptionResponse updateOne(ModeReceptionRequest request) {
        log.info("Updating ModeReception with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("ModeReception trackingId should not be null !");

        ModeReception modeReception = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("ModeReception with id: "+request.getTrackingId()+" not found!"));
        modeReception.setDataStatus(DataStatus.UPDATED);
        ModeReceptionResponse modeReceptionResponse = modeReceptionMapper.toDto(repository.save(modeReception));
        log.info("ModeReception is updated with id: {}", modeReception.getId());
        log.info("Returning ModeReceptionResponse with trackingId: {}", modeReceptionResponse.getTrackingId());
        return modeReceptionResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting ModeReception with trackingId:: "+trackingId);
        ModeReception modeReception = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("ModeReception with id: "+trackingId+" not found!"));
        modeReception.setDataStatus(DataStatus.DELETED);
        repository.save(modeReception);
        log.info("ModeReception is deleted with id: {}", modeReception.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("ModeReception total count : {}", count);
        return count;
    }
}
