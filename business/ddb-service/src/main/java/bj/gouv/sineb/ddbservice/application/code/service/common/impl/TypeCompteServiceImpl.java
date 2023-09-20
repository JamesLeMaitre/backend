package bj.gouv.sineb.ddbservice.application.code.service.common.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.aae.TypeStructureRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.request.common.TypeCompteRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.aae.TypeStructureResponse;
import bj.gouv.sineb.ddbservice.application.code.dto.response.common.TypeCompteResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.TypeStructure;
import bj.gouv.sineb.ddbservice.application.code.entity.common.TypeCompte;
import bj.gouv.sineb.ddbservice.application.code.mapper.common.TypeCompteMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.common.TypeCompteRepository;
import bj.gouv.sineb.ddbservice.application.code.service.common.TypeCompteService;
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
public class TypeCompteServiceImpl implements TypeCompteService {

    private final TypeCompteRepository repository;

    private final TypeCompteMapper mapper;

    private final StreamBridge bridge;

    /**
     * @param request
     * @return
     */
    @Override
    @Transactional
    public TypeCompteResponse save(TypeCompteRequest request) {
        log.info("Saving TypeCompte with payload: "+request.toString());
        TypeCompte typeCompte = mapper.toEntity(request);
        typeCompte.setTrackingId(UUID.randomUUID());
        TypeCompteResponse typeCompteResponse = mapper.toDto(repository.save(typeCompte));
        //Send into kafka
        producer(typeCompteResponse);
        log.info("TypeCompte is created with id: {}", typeCompte.getId());
        log.info("Returning TypeCompteResponse with trackingId: {}", typeCompteResponse.getTrackingId());
        return typeCompteResponse;
    }


    /**
     * @param request
     * @param trackingId
     * @return
     */
    @Override
    @Transactional
    public TypeCompteResponse update(TypeCompteRequest request, UUID trackingId) {
        if (trackingId == null)
            throw new CustomException("Country trackingId should not be null !");
        TypeCompte typeCompte = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow();
        typeCompte.setDataStatus(DataStatus.UPDATED);
        TypeCompteResponse typeCompteResponse = mapper.toDto(repository.save(typeCompte));
        // Send into kafka
        producer(typeCompteResponse);
        log.info("TypeCompte is updated with id: {}", typeCompte.getId());
        log.info("Returning TypeCompteResponse with trackingId: {}", typeCompteResponse.getTrackingId());
        return typeCompteResponse;
    }





    /**
     * @param trackingId
     * @return
     */
    @Override
    public TypeCompteResponse delete(UUID trackingId) {
        log.info("Deleting TypeCompte with trackingId:: " + trackingId);
        TypeCompte typeCompte = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow(() -> new CustomNotFoundException("TypeCompte with id: " + trackingId + " not found!"));
        typeCompte.setDataStatus(DataStatus.DELETED);
        TypeCompteResponse typeCompteResponse = mapper.toDto(repository.save(typeCompte));
        //Send into kafka
        producer(typeCompteResponse);
        log.info("TypeCompte is deleted with id: {}", typeCompte.getId());
        return typeCompteResponse;
    }

    /**
     * @param trackingId
     * @return
     */
    @Override
    public TypeCompteResponse getOne(UUID trackingId) {
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
    public Page<TypeCompteResponse> getAll(int page, int size) {
        return repository.findTypeCompteByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size))
                .map(mapper::toDto);
    }

    /**
     * @return 
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("TypeCompte total count : {}", count);
        return count;
    }

    private void producer(TypeCompteResponse response) {
        bridge.send("type-compte-out-0", response);
    }
}

