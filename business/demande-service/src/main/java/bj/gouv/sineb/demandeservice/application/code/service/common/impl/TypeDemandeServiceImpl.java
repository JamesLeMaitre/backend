package bj.gouv.sineb.demandeservice.application.code.service.common.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.demandeservice.application.code.entity.common.TypeDemande;
import bj.gouv.sineb.demandeservice.application.code.mapper.common.TypeDemandeMapper;
import bj.gouv.sineb.demandeservice.application.code.repository.common.TypeDemandeRepository;
import bj.gouv.sineb.demandeservice.application.code.service.common.TypeDemandeService;
import bj.gouv.sineb.demandeservice.common.dto.request.common.TypeDemandeRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.common.TypeDemandeResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TypeDemandeServiceImpl implements TypeDemandeService {
    
    private final TypeDemandeRepository repository;
    
    private final TypeDemandeMapper mapper;
    
    /**
     * @param request 
     * @return
     */
    @Override
    public TypeDemandeResponse save(TypeDemandeRequest request) {
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
    public TypeDemandeResponse update(TypeDemandeRequest request, UUID trackingId) {

        if (trackingId == null)
            throw new CustomException("Country trackingId should not be null !");
        TypeDemande typeDemande = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow();
        typeDemande.setDataStatus(DataStatus.UPDATED);

        return Optional.of(request)
                .map(TypeDemandeRequest -> mapper.toEntity(request, typeDemande))
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow();
    }

    /**
     * @param trackingId 
     * @return
     */
    @Override
    public TypeDemandeResponse delete(UUID trackingId) {
        return repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .stream()
                .peek(typeDemande -> typeDemande.setDataStatus(DataStatus.DELETED))
                .map(repository::save)
                .map(mapper::toDto)
                .findFirst()
                .orElseThrow(() -> new CustomNotFoundException("TypeDemande not found for id "+trackingId));
    }

    /**
     * @param trackingId 
     * @return
     */
    @Override
    public TypeDemandeResponse getOne(UUID trackingId) {
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
    public Page<TypeDemandeResponse> getAll(int page, int size) {
        return repository.findTypeDemandeByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size))
                .map(mapper::toDto);
    }
}
