package bj.gouv.sineb.ddbservice.application.code.service.common.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.CategoryEltRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.CategoryEltResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.common.CategoryElt;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.CategoryEltMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.CategoryEltRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.CategoryEltService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class CategoryEltServiceImpl implements CategoryEltService {
    private final CategoryEltRepository repository;
    private final CategoryEltMapper categoryEltMapper;

    public CategoryEltServiceImpl(CategoryEltRepository repository, CategoryEltMapper categoryEltMapper) {
        this.repository = repository;
        this.categoryEltMapper = categoryEltMapper;
    }


    @Override
    @Transactional
    public CategoryEltResponse save(CategoryEltRequest request) {
        log.info("Saving CategoryElt with payload: "+request.toString());
        CategoryElt categoryElt = categoryEltMapper.toEntity(request);
        categoryElt.setDataStatus(DataStatus.CREATED);
        CategoryEltResponse categoryEltResponse = categoryEltMapper.toDto(repository.save(categoryElt));
        log.info("CategoryElt is created with id: {}", categoryElt.getId());
        log.info("Returning CategoryEltResponse with trackingId: {}", categoryEltResponse.getTrackingId());
        return categoryEltResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public CategoryEltResponse getOne(UUID id) {
        log.info("Searching for CategoryElt with id: "+id);
        CategoryElt categoryElt = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("CategoryElt with id: "+id+" not found!"));
        CategoryEltResponse categoryEltResponse = categoryEltMapper.toDto(categoryElt);
        log.info("CategoryElt is found with id: {}", categoryElt.getId());
        log.info("Returning CategoryEltResponse with trackingId: {}", categoryEltResponse.getTrackingId());
        return categoryEltResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryEltResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for CategoryElt with trackingId: "+trackingId);
        CategoryElt categoryElt = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("CategoryElt with trackingId: "+trackingId+" not found!"));
        CategoryEltResponse categoryEltResponse = categoryEltMapper.toDto(categoryElt);
        log.info("CategoryElt is found with trackingId: {}", categoryElt.getTrackingId());
        log.info("Returning CategoryEltResponse with trackingId: {}", categoryEltResponse.getTrackingId());
        return categoryEltResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryEltResponse> getAll(int page, int size) {
        log.info("Searching for CategoryElt list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<CategoryElt> categoryEltPage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<CategoryEltResponse> categoryEltPageResponse = categoryEltPage.map(categoryEltMapper::toDto);
        log.info("CategoryElt list found with size: {}", categoryEltPage.getTotalElements());
        log.info("Returning CategoryEltResponse list with size: {}", categoryEltPage.getTotalElements());
        return categoryEltPageResponse;
    }

    @Override
    @Transactional
    public CategoryEltResponse updateOne(CategoryEltRequest request) {
        log.info("Updating CategoryElt with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("CategoryElt trackingId should not be null !");

        CategoryElt categoryElt = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("CategoryElt with id: "+request.getTrackingId()+" not found!"));
        categoryElt.setDataStatus(DataStatus.UPDATED);
        CategoryEltResponse categoryEltResponse = categoryEltMapper.toDto(repository.save(categoryElt));
        log.info("CategoryElt is updated with id: {}", categoryElt.getId());
        log.info("Returning CategoryEltResponse with trackingId: {}", categoryEltResponse.getTrackingId());
        return categoryEltResponse;
    }



    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting CategoryElt with trackingId:: "+trackingId);
        CategoryElt categoryElt = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("CategoryElt with id: "+trackingId+" not found!"));
        categoryElt.setDataStatus(DataStatus.DELETED);
        repository.save(categoryElt);
        log.info("CategoryElt is deleted with id: {}", categoryElt.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("CategoryElt total count : {}", count);
        return count;
    }
}
