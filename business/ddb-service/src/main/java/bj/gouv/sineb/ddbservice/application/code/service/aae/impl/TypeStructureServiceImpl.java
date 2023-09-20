package bj.gouv.sineb.ddbservice.application.code.service.aae.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.CiviliteRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.TypeStructureRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.CiviliteResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.TypeStructureResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.Civilite;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.TypeStructure;
import bj.gouv.sineb.ddbservice.application.code.mapper.aae.TypeStructureMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.aae.TypeStructureRepository;
import bj.gouv.sineb.ddbservice.application.code.service.aae.TypeStructureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TypeStructureServiceImpl implements TypeStructureService {

    private final TypeStructureRepository repository;

    private final TypeStructureMapper mapper;
    private final StreamBridge bridge;

    /**
     * @param request
     * @return
     */
    @Override
    @Transactional
    public TypeStructureResponse save(TypeStructureRequest request) {
        log.info("Saving TypeStructure with payload: "+request.toString());
        TypeStructure typeStructure = mapper.toEntity(request);
        typeStructure.setTrackingId(UUID.randomUUID());
        typeStructure.setDataStatus(DataStatus.CREATED);
        TypeStructureResponse typeStructureResponse = mapper.toDto(repository.save(typeStructure));
        //Send into kafka
        producer(typeStructureResponse);
        log.info("TypeStructure is created with id: {}", typeStructure.getId());
        log.info("Returning TypeStructureResponse with trackingId: {}", typeStructureResponse.getTrackingId());
        return typeStructureResponse;
    }

    /**
     * @param request
     * @param trackingId
     * @return
     */
    @Override
    @Transactional
    public TypeStructureResponse update(TypeStructureRequest request, UUID trackingId) {
        if (trackingId == null)
            throw new CustomException("Country trackingId should not be null !");
        TypeStructure typeStructure = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow();
        typeStructure.setDataStatus(DataStatus.UPDATED);
        TypeStructureResponse typeStructureResponse = mapper.toDto(repository.save(typeStructure));
        // Send into kafka
        producer(typeStructureResponse);
        log.info("TypeStructure is updated with id: {}", typeStructure.getId());
        log.info("Returning TypeStructureResponse with trackingId: {}", typeStructureResponse.getTrackingId());
        return typeStructureResponse;
    }


    /**
     * @param trackingId
     * @return
     */
    @Override
    @Transactional
    public TypeStructureResponse delete(UUID trackingId) {
        log.info("Deleting TypeStructure with trackingId:: " + trackingId);
        TypeStructure typeStructure = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow(() -> new CustomNotFoundException("TypeStructure with id: " + trackingId + " not found!"));
        typeStructure.setDataStatus(DataStatus.DELETED);
        TypeStructureResponse typeStructureResponse = mapper.toDto(repository.save(typeStructure));
        //Send into kafka
        producer(typeStructureResponse);
        log.info("TypeStructure is deleted with id: {}", typeStructure.getId());
        return typeStructureResponse;


    }

    /**
     * @param trackingId
     * @return
     */
    @Override
    public TypeStructureResponse getOne(UUID trackingId) {
        return repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .map(mapper::toDto)
                .orElseThrow();
    }

    /**
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<TypeStructureResponse> getAll(int page, int size) {
        return repository.findTypeStructureByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size))
                .map(mapper::toDto);
    }

    /**
     * @return 
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("TypeStructure total count : {}", count);
        return count;
    }

    private void producer(TypeStructureResponse response) {
        bridge.send("type-structure-out-0", response);
    }
}
