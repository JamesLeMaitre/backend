package bj.gouv.sineb.ddbservice.application.code.service.ppe.impl;


import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.dto.request.ppe.BeneficiaireCategoryProjetRequest;
import bj.gouv.sineb.ddbservice.application.code.dto.response.ppe.BeneficiaireCategoryProjetResponse;
import bj.gouv.sineb.ddbservice.application.code.entity.ppe.BeneficiaireCategoryProjet;
import bj.gouv.sineb.ddbservice.application.code.mapper.ppe.BeneficiaireCategoryProjetMapper;
import bj.gouv.sineb.ddbservice.application.code.repository.ppe.BeneficiaireCategoryProjetRepository;
import bj.gouv.sineb.ddbservice.application.code.service.ppe.BeneficiaireCategoryProjetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class BeneficiaireCategoryProjetServiceImpl implements BeneficiaireCategoryProjetService {
    private final BeneficiaireCategoryProjetRepository repository;
    private final BeneficiaireCategoryProjetMapper beneficiaireCategoryProjetMapper;

    public BeneficiaireCategoryProjetServiceImpl(BeneficiaireCategoryProjetRepository repository, BeneficiaireCategoryProjetMapper beneficiaireCategoryProjetMapper) {
        this.repository = repository;
        this.beneficiaireCategoryProjetMapper = beneficiaireCategoryProjetMapper;
    }


    @Override
    @Transactional
    public BeneficiaireCategoryProjetResponse save(BeneficiaireCategoryProjetRequest request) {
        log.info("Saving BeneficiaireCategoryProjet with payload: "+request.toString());
        BeneficiaireCategoryProjet beneficiaireCategoryProjet = beneficiaireCategoryProjetMapper.toEntity(request);
        beneficiaireCategoryProjet.setDataStatus(DataStatus.CREATED);
        BeneficiaireCategoryProjetResponse beneficiaireCategoryProjetResponse = beneficiaireCategoryProjetMapper.toDto(repository.save(beneficiaireCategoryProjet));
        log.info("BeneficiaireCategoryProjet is created with id: {}", beneficiaireCategoryProjet.getId());
        log.info("Returning BeneficiaireCategoryProjetResponse with trackingId: {}", beneficiaireCategoryProjetResponse.getTrackingId());
        return beneficiaireCategoryProjetResponse;
    }


    @Override
    @Transactional(readOnly = true)
    public BeneficiaireCategoryProjetResponse getOne(UUID id) {
        log.info("Searching for BeneficiaireCategoryProjet with id: "+id);
        BeneficiaireCategoryProjet beneficiaireCategoryProjet = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("BeneficiaireCategoryProjet with id: "+id+" not found!"));
        BeneficiaireCategoryProjetResponse beneficiaireCategoryProjetResponse = beneficiaireCategoryProjetMapper.toDto(beneficiaireCategoryProjet);
        log.info("BeneficiaireCategoryProjet is found with id: {}", beneficiaireCategoryProjet.getId());
        log.info("Returning BeneficiaireCategoryProjetResponse with trackingId: {}", beneficiaireCategoryProjetResponse.getTrackingId());
        return beneficiaireCategoryProjetResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public BeneficiaireCategoryProjetResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for BeneficiaireCategoryProjet with trackingId: "+trackingId);
        BeneficiaireCategoryProjet beneficiaireCategoryProjet = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("BeneficiaireCategoryProjet with trackingId: "+trackingId+" not found!"));
        BeneficiaireCategoryProjetResponse beneficiaireCategoryProjetResponse = beneficiaireCategoryProjetMapper.toDto(beneficiaireCategoryProjet);
        log.info("BeneficiaireCategoryProjet is found with trackingId: {}", beneficiaireCategoryProjet.getTrackingId());
        log.info("Returning BeneficiaireCategoryProjetResponse with trackingId: {}", beneficiaireCategoryProjetResponse.getTrackingId());
        return beneficiaireCategoryProjetResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BeneficiaireCategoryProjetResponse> getAll(int page, int size) {
        log.info("Searching for BeneficiaireCategoryProjet list with (page, size, deleted) : "+"("+page+", "+size+", "+DataStatus.DELETED+")");
        Page<BeneficiaireCategoryProjet> beneficiaireCategoryProjetPage = repository.findAllByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size));
        Page<BeneficiaireCategoryProjetResponse> beneficiaireCategoryProjetPageResponse = beneficiaireCategoryProjetPage.map(beneficiaireCategoryProjetMapper::toDto);
        log.info("BeneficiaireCategoryProjet list found with size: {}", beneficiaireCategoryProjetPage.getTotalElements());
        log.info("Returning BeneficiaireCategoryProjetResponse list with size: {}", beneficiaireCategoryProjetPage.getTotalElements());
        return beneficiaireCategoryProjetPageResponse;
    }

    @Override
    @Transactional
    public BeneficiaireCategoryProjetResponse updateOne(BeneficiaireCategoryProjetRequest request) {
        log.info("Updating BeneficiaireCategoryProjet with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("BeneficiaireCategoryProjet trackingId should not be null !");

        BeneficiaireCategoryProjet beneficiaireCategoryProjet = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("BeneficiaireCategoryProjet with id: "+request.getTrackingId()+" not found!"));
        beneficiaireCategoryProjet.setDataStatus(DataStatus.UPDATED);
        BeneficiaireCategoryProjetResponse beneficiaireCategoryProjetResponse = beneficiaireCategoryProjetMapper.toDto(repository.save(beneficiaireCategoryProjet));
        log.info("BeneficiaireCategoryProjet is updated with id: {}", beneficiaireCategoryProjet.getId());
        log.info("Returning BeneficiaireCategoryProjetResponse with trackingId: {}", beneficiaireCategoryProjetResponse.getTrackingId());
        return beneficiaireCategoryProjetResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting BeneficiaireCategoryProjet with trackingId:: "+trackingId);
        BeneficiaireCategoryProjet beneficiaireCategoryProjet = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("BeneficiaireCategoryProjet with id: "+trackingId+" not found!"));
        beneficiaireCategoryProjet.setDataStatus(DataStatus.DELETED);
        repository.save(beneficiaireCategoryProjet);
        log.info("BeneficiaireCategoryProjet is deleted with id: {}", beneficiaireCategoryProjet.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countAllByDataStatusIsNot(DataStatus.DELETED);
        log.info("BeneficiaireCategoryProjet total count : {}", count);
        return count;
    }
}
