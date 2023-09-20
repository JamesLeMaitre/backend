package bj.gouv.sineb.ddbservice.application.code.service.sie.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.sie.BlocActiviteEconomiqueRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.sie.BlocActiviteEconomiqueResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.sie.BlocActiviteEconomique;
import bj.gouv.sineb.ddbservice.application.code.mapper.sie.BlocActiviteEconomiqueMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.sie.BlocActiviteEconomiqueRepository;
import bj.gouv.sineb.ddbservice.application.code.service.sie.BlocActiviteEconomiqueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class BlocActiviteEconomiqueServiceImpl implements BlocActiviteEconomiqueService {
    private final BlocActiviteEconomiqueRepository repository;
    private final BlocActiviteEconomiqueMapper blocActiviteEconomiqueMapper;

    public BlocActiviteEconomiqueServiceImpl(BlocActiviteEconomiqueRepository repository, BlocActiviteEconomiqueMapper blocActiviteEconomiqueMapper) {
        this.repository = repository;
        this.blocActiviteEconomiqueMapper = blocActiviteEconomiqueMapper;
    }


    @Override
    @Transactional
    public BlocActiviteEconomiqueResponse save(BlocActiviteEconomiqueRequest request) {
        log.info("Saving BlocActiviteEconomique with payload: "+request.toString());
        BlocActiviteEconomique blocActiviteEconomique = blocActiviteEconomiqueMapper.toEntity(request);
        blocActiviteEconomique.setDataStatus(DataStatus.CREATED);
        BlocActiviteEconomiqueResponse blocActiviteEconomiqueResponse = blocActiviteEconomiqueMapper.toDto(repository.save(blocActiviteEconomique));
        log.info("BlocActiviteEconomique is created with id: {}", blocActiviteEconomique.getId());
        log.info("Returning BlocActiviteEconomiqueResponse with trackingId: {}", blocActiviteEconomiqueResponse.getTrackingId());
        return blocActiviteEconomiqueResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public BlocActiviteEconomiqueResponse getOne(UUID id) {
        log.info("Searching for BlocActiviteEconomique with id: "+id);
        BlocActiviteEconomique blocActiviteEconomique = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("BlocActiviteEconomique with id: "+id+" not found!"));
        BlocActiviteEconomiqueResponse blocActiviteEconomiqueResponse = blocActiviteEconomiqueMapper.toDto(blocActiviteEconomique);
        log.info("BlocActiviteEconomique is found with id: {}", blocActiviteEconomique.getId());
        log.info("Returning BlocActiviteEconomiqueResponse with trackingId: {}", blocActiviteEconomiqueResponse.getTrackingId());
        return blocActiviteEconomiqueResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public BlocActiviteEconomiqueResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for BlocActiviteEconomique with trackingId: "+trackingId);
        BlocActiviteEconomique blocActiviteEconomique = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("BlocActiviteEconomique with trackingId: "+trackingId+" not found!"));
        BlocActiviteEconomiqueResponse blocActiviteEconomiqueResponse = blocActiviteEconomiqueMapper.toDto(blocActiviteEconomique);
        log.info("BlocActiviteEconomique is found with trackingId: {}", blocActiviteEconomique.getTrackingId());
        log.info("Returning BlocActiviteEconomiqueResponse with trackingId: {}", blocActiviteEconomiqueResponse.getTrackingId());
        return blocActiviteEconomiqueResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlocActiviteEconomiqueResponse> getAll(int page, int size) {
        log.info("Searching for BlocActiviteEconomique list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<BlocActiviteEconomique> blocActiviteEconomiquePage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<BlocActiviteEconomiqueResponse> blocActiviteEconomiquePageResponse = blocActiviteEconomiquePage.map(blocActiviteEconomiqueMapper::toDto);
        log.info("BlocActiviteEconomique list found with size: {}", blocActiviteEconomiquePage.getTotalElements());
        log.info("Returning BlocActiviteEconomiqueResponse list with size: {}", blocActiviteEconomiquePage.getTotalElements());
        return blocActiviteEconomiquePageResponse;
    }

    @Override
    @Transactional
    public BlocActiviteEconomiqueResponse updateOne(BlocActiviteEconomiqueRequest request) {
        log.info("Updating BlocActiviteEconomique with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("BlocActiviteEconomique trackingId should not be null !");

        BlocActiviteEconomique blocActiviteEconomique = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("BlocActiviteEconomique with id: "+request.getTrackingId()+" not found!"));
        blocActiviteEconomique.setDataStatus(DataStatus.UPDATED);
        BlocActiviteEconomiqueResponse blocActiviteEconomiqueResponse = blocActiviteEconomiqueMapper.toDto(repository.save(blocActiviteEconomique));
        log.info("BlocActiviteEconomique is updated with id: {}", blocActiviteEconomique.getId());
        log.info("Returning BlocActiviteEconomiqueResponse with trackingId: {}", blocActiviteEconomiqueResponse.getTrackingId());
        return blocActiviteEconomiqueResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting BlocActiviteEconomique with trackingId:: "+trackingId);
        BlocActiviteEconomique blocActiviteEconomique = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("BlocActiviteEconomique with id: "+trackingId+" not found!"));
        blocActiviteEconomique.setDataStatus(DataStatus.DELETED);
        repository.save(blocActiviteEconomique);
        log.info("BlocActiviteEconomique is deleted with id: {}", blocActiviteEconomique.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("BlocActiviteEconomique total count : {}", count);
        return count;
    }
}
