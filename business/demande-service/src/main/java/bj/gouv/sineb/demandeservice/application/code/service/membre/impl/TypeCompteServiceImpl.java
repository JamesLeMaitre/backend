package bj.gouv.sineb.demandeservice.application.code.service.membre.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.demandeservice.application.code.entity.membre.TypeCompte;
import bj.gouv.sineb.demandeservice.application.code.mapper.membre.TypeCompteMapper;
import bj.gouv.sineb.demandeservice.application.code.repository.membre.TypeCompteRepository;
import bj.gouv.sineb.demandeservice.application.code.service.membre.TypeCompteService;
import bj.gouv.sineb.demandeservice.common.dto.request.membre.TypeCompteRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.membre.TypeCompteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TypeCompteServiceImpl implements TypeCompteService {
    
    private final TypeCompteRepository repository;
    
    private final TypeCompteMapper  mapper;
    
    /**
     * @param request 
     * @return
     */
    @Override
    public TypeCompteResponse save(TypeCompteRequest request) {
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
    public TypeCompteResponse update(TypeCompteRequest request, UUID trackingId) {
        if (trackingId == null)
            throw new CustomException("Country trackingId should not be null !");
        TypeCompte typeCompte = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow();
        typeCompte.setDataStatus(DataStatus.UPDATED);
        return Optional.of(request)
                .map(TypeCompteRequest -> mapper.toEntity(request, typeCompte))
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow();
    }

    /**
     * @param trackingId 
     * @return
     */
    @Override
    public TypeCompteResponse delete(UUID trackingId) {
        return repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .stream()
                .peek(typeCompte -> typeCompte.setDataStatus(DataStatus.DELETED))
                .map(repository::save)
                .map(mapper::toDto)
                .findFirst()
                .orElseThrow(() -> new CustomNotFoundException("TypeCompte not found for id "+trackingId));
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
}
