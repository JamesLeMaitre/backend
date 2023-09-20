package bj.gouv.sineb.ddbservice.application.code.service.common.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.SousCategoryEltRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.SousCategoryEltResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.SousCategoryElt;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.SousCategoryEltMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.SousCategoryEltRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.SousCategoryEltService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class SousCategoryEltServiceImpl implements SousCategoryEltService {
    private final SousCategoryEltRepository repository;
    private final SousCategoryEltMapper cousCategoryEltMapper;

    public SousCategoryEltServiceImpl(SousCategoryEltRepository repository, SousCategoryEltMapper cousCategoryEltMapper) {
        this.repository = repository;
        this.cousCategoryEltMapper = cousCategoryEltMapper;
    }


    @Override
    @Transactional
    public SousCategoryEltResponse save(SousCategoryEltRequest request) {
        log.info("Saving SousCategoryElt with payload: "+request.toString());
        SousCategoryElt cousCategoryElt = cousCategoryEltMapper.toEntity(request);
        cousCategoryElt.setDataStatus(DataStatus.CREATED);
        SousCategoryEltResponse cousCategoryEltResponse = cousCategoryEltMapper.toDto(repository.save(cousCategoryElt));
        log.info("SousCategoryElt is created with id: {}", cousCategoryElt.getId());
        log.info("Returning SousCategoryEltResponse with trackingId: {}", cousCategoryEltResponse.getTrackingId());
        return cousCategoryEltResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public SousCategoryEltResponse getOne(UUID id) {
        log.info("Searching for SousCategoryElt with id: "+id);
        SousCategoryElt cousCategoryElt = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("SousCategoryElt with id: "+id+" not found!"));
        SousCategoryEltResponse cousCategoryEltResponse = cousCategoryEltMapper.toDto(cousCategoryElt);
        log.info("SousCategoryElt is found with id: {}", cousCategoryElt.getId());
        log.info("Returning SousCategoryEltResponse with trackingId: {}", cousCategoryEltResponse.getTrackingId());
        return cousCategoryEltResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public SousCategoryEltResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for SousCategoryElt with trackingId: "+trackingId);
        SousCategoryElt cousCategoryElt = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("SousCategoryElt with trackingId: "+trackingId+" not found!"));
        SousCategoryEltResponse cousCategoryEltResponse = cousCategoryEltMapper.toDto(cousCategoryElt);
        log.info("SousCategoryElt is found with trackingId: {}", cousCategoryElt.getTrackingId());
        log.info("Returning SousCategoryEltResponse with trackingId: {}", cousCategoryEltResponse.getTrackingId());
        return cousCategoryEltResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SousCategoryEltResponse> getAll(int page, int size) {
        log.info("Searching for SousCategoryElt list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<SousCategoryElt> cousCategoryEltPage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<SousCategoryEltResponse> cousCategoryEltPageResponse = cousCategoryEltPage.map(cousCategoryEltMapper::toDto);
        log.info("SousCategoryElt list found with size: {}", cousCategoryEltPage.getTotalElements());
        log.info("Returning SousCategoryEltResponse list with size: {}", cousCategoryEltPage.getTotalElements());
        return cousCategoryEltPageResponse;
    }

    @Override
    public Page<SousCategoryEltResponse> getAllByCategoryId(UUID categoryTackingId, int page, int size) {
        log.info("Searching for SousCategoryElt list by category with (categoryTackingId, page, size, deleted) : "+"("+categoryTackingId+", "+page+", "+size+", "+DataStatus.DELETED+")");
        Page<SousCategoryElt> cousCategoryEltPage = repository.findAllByDataStatusIsNotAndCategoryEltTrackingId(DataStatus.DELETED, categoryTackingId, PageRequest.of(page, size));
        Page<SousCategoryEltResponse> cousCategoryEltPageResponse = cousCategoryEltPage.map(cousCategoryEltMapper::toDto);
        log.info("SousCategoryElt list found with size: {}", cousCategoryEltPage.getTotalElements());
        log.info("Returning SousCategoryEltResponse list with size: {}", cousCategoryEltPage.getTotalElements());
        return cousCategoryEltPageResponse;
    }

    @Override
    @Transactional
    public SousCategoryEltResponse updateOne(SousCategoryEltRequest request) {
        log.info("Updating SousCategoryElt with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("SousCategoryElt trackingId should not be null !");

        SousCategoryElt cousCategoryElt = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("SousCategoryElt with id: "+request.getTrackingId()+" not found!"));
        cousCategoryElt.setDataStatus(DataStatus.UPDATED);
        SousCategoryEltResponse cousCategoryEltResponse = cousCategoryEltMapper.toDto(repository.save(cousCategoryElt));
        log.info("SousCategoryElt is updated with id: {}", cousCategoryElt.getId());
        log.info("Returning SousCategoryEltResponse with trackingId: {}", cousCategoryEltResponse.getTrackingId());
        return cousCategoryEltResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting SousCategoryElt with trackingId:: "+trackingId);
        SousCategoryElt cousCategoryElt = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("SousCategoryElt with id: "+trackingId+" not found!"));
        cousCategoryElt.setDataStatus(DataStatus.DELETED);
        repository.save(cousCategoryElt);
        log.info("SousCategoryElt is deleted with id: {}", cousCategoryElt.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("SousCategoryElt total count : {}", count);
        return count;
    }
}
