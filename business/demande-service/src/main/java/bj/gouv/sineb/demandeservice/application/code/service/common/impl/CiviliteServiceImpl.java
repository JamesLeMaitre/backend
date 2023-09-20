package bj.gouv.sineb.demandeservice.application.code.service.common.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.demandeservice.application.code.entity.common.Civilite;
import bj.gouv.sineb.demandeservice.application.code.mapper.common.CiviliteMapper;
import bj.gouv.sineb.demandeservice.application.code.repository.common.CiviliteRepository;
import bj.gouv.sineb.demandeservice.application.code.service.common.CiviliteService;
import bj.gouv.sineb.demandeservice.common.dto.request.common.CiviliteRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.common.CiviliteResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CiviliteServiceImpl implements CiviliteService {

    private final CiviliteRepository repository;

    private final CiviliteMapper mapper;

    @Bean
    public Consumer<CiviliteResponse> eventCivilityConsumer() {
        return (input) -> {
            Civilite civilite = new Civilite();

            DataStatus status = input.getDataStatus();

            switch (status) {
                case CREATED -> {
                    BeanUtils.copyProperties(input, civilite);
                    repository.save(civilite);
                }
                case UPDATED, DELETED -> {
                  civilite = repository.findCiviliteByDataStatusIsNotAndTrackingId(DataStatus.DELETED, input.getTrackingId())
                          .orElseThrow();
                    BeanUtils.copyProperties(input, civilite);
                    repository.save(civilite);
                }
                default -> {
                    log.info("No data found");
                }
            }

        };
    }

    @Override
    public CiviliteResponse save(CiviliteRequest request) {
        return Optional.of(request).stream()
                .map(mapper::toEntity)
                .peek(repository::save)
                .map(mapper::toDto)
                .findFirst()
                .orElseThrow(() -> new CustomNotFoundException("Error while saving Civilite"));
    }

    @Override
    public CiviliteResponse update(CiviliteRequest request, UUID trackingId) {

        if (trackingId == null)
            throw new CustomException("Country trackingId should not be null !");

        Civilite civilite = repository.findCiviliteByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow(() -> new CustomNotFoundException("Civilite not found for id "+trackingId));
        civilite.setDataStatus(DataStatus.UPDATED);
        return Optional.of(request)
                .map(civiliteRequest -> mapper.toEntity(request, civilite))
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new CustomNotFoundException("Error while updating Civilite"));
    }

    @Override
    public CiviliteResponse delete(UUID trackingId) {

        return repository.findCiviliteByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .stream()
                .peek(civilite -> civilite.setDataStatus(DataStatus.DELETED))
                .map(repository::save)
                .map(mapper::toDto)
                .findFirst()
                .orElseThrow(() -> new CustomNotFoundException("Civilite not found"));
    }

    @Override
    public CiviliteResponse getOne(UUID trackingId) {
        return repository.findCiviliteByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .map(mapper::toDto)
                .orElseThrow(() -> new CustomNotFoundException("Civilite not found"));
    }

    @Override
    public Page<CiviliteResponse> getAll(int page, int size) {
        return repository.findCiviliteByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size))
                .map(mapper::toDto);
    }
}
