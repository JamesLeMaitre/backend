package bj.gouv.sineb.demandeservice.application.code.service.common.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.demandeservice.application.code.entity.common.TypeStructure;
import bj.gouv.sineb.demandeservice.application.code.mapper.common.TypeStructureMapper;
import bj.gouv.sineb.demandeservice.application.code.repository.common.TypeStructureRepository;
import bj.gouv.sineb.demandeservice.application.code.service.common.TypeStructureService;
import bj.gouv.sineb.demandeservice.common.dto.request.common.TypeStructureRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.common.TypeStructureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TypeStructureServiceImpl implements TypeStructureService {
    
    private final TypeStructureRepository repository;
    
    private final TypeStructureMapper mapper;
    
    /**
     * @param request 
     * @return
     */
    @Override
    public TypeStructureResponse save(TypeStructureRequest request) {
        return Optional.of(request).stream()
                .map(mapper::toEntity)
                .peek(repository::save)
                .map(mapper::toDto)
                .findFirst()
                .orElseThrow();
    }

    /**
     * @param request 
     * @param trackingId
     * @return
     */
    @Override
    public TypeStructureResponse update(TypeStructureRequest request, UUID trackingId) {
        if (trackingId == null)
            throw new CustomException("Country trackingId should not be null !");
        TypeStructure typeStructure = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow();
        typeStructure.setDataStatus(DataStatus.UPDATED);
        return Optional.of(request)
                .map(TypeStructureRequest -> mapper.toEntity(request, typeStructure))
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow();
    }

    /**
     * @param trackingId 
     * @return
     */
    @Override
    public TypeStructureResponse delete(UUID trackingId) {
        return repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .stream()
                .peek(typeStructure -> typeStructure.setDataStatus(DataStatus.DELETED))
                .map(repository::save)
                .map(mapper::toDto)
                .findFirst()
                .orElseThrow(() -> new CustomNotFoundException("TypeStructure not found for id "+trackingId));
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
}
