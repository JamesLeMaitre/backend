package bj.gouv.sineb.demandeservice.application.code.service.common.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.demandeservice.application.code.entity.common.Civilite;
import bj.gouv.sineb.demandeservice.application.code.entity.common.Pays;
import bj.gouv.sineb.demandeservice.application.code.mapper.common.PaysMapper;
import bj.gouv.sineb.demandeservice.application.code.repository.common.PaysRepository;
import bj.gouv.sineb.demandeservice.application.code.service.common.PaysService;
import bj.gouv.sineb.demandeservice.common.dto.request.common.PaysRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.common.CiviliteResponse;
import bj.gouv.sineb.demandeservice.common.dto.response.common.PaysResponse;
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
public class PaysServiceImpl implements PaysService {

    private final PaysRepository repository;

    private final PaysMapper mapper;


    @Bean
    public Consumer<PaysResponse> eventPaysConsumer() {
        return (input) -> {
            System.out.println("************************************");
            System.out.println(input);
            System.out.println("************************************");
            Pays pays = new Pays();
            DataStatus status = input.getDataStatus();

            switch (status) {
                case CREATED -> {
                    BeanUtils.copyProperties(input, pays);
                    repository.save(pays);
                }
                case UPDATED, DELETED -> {
                    pays = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, input.getTrackingId())
                            .orElseThrow();
                    BeanUtils.copyProperties(input, pays);
                    repository.save(pays);
                }
                default -> {
                    log.info("No data found");
                }
            }

        };
    }

    /**
     * @param request 
     * @return
     */
    @Override
    public PaysResponse save(PaysRequest request) {
        return Optional.of(request).stream()
                .map(mapper::toEntity)
                .peek(repository::save)
                .map(mapper::toDto)
                .findFirst()
                .orElseThrow(() -> new CustomNotFoundException("Pays not found"));
    }

    /**
     * @param request 
     * @param trackingId
     * @return
     */
    @Override
    public PaysResponse update(PaysRequest request, UUID trackingId) {

        if (trackingId == null)
            throw new CustomException("Country trackingId should not be null !");
        Pays pays = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow();

        return Optional.of(request)
                .map(paysRequest -> mapper.toEntity(request, pays))
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new CustomNotFoundException("Pays not found"));
    }

    /**
     * @param trackingId 
     * @return
     */
    @Override
    public PaysResponse delete(UUID trackingId) {
        return repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .stream()
                .peek(pays -> pays.setDataStatus(DataStatus.DELETED))
                .map(repository::save)
                .map(mapper::toDto)
                .findFirst()
                .orElseThrow(() -> new CustomNotFoundException("Pays not found"));
    }

    /**
     * @param trackingId 
     * @return
     */
    @Override
    public PaysResponse getOne(UUID trackingId) {
        return repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .map(mapper::toDto)
                .orElseThrow(() -> new CustomNotFoundException("Pays not found"));
    }

    /**
     * @param page 
     * @param size
     * @return
     */
    @Override
    public Page<PaysResponse> getAll(int page, int size) {
        return repository.findPaysByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size))
                .map(mapper::toDto);
    }
}
