package bj.gouv.sineb.authservice.application.code.service.impl;

import bj.gouv.sineb.authservice.application.code.entity.Groupe;
import bj.gouv.sineb.authservice.application.code.mapper.GroupeMapper;
import bj.gouv.sineb.authservice.application.code.dto.request.GroupeRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.GroupeResponse;
import bj.gouv.sineb.authservice.application.code.repository.GroupeRepository;
import bj.gouv.sineb.authservice.application.code.service.GroupeService;
import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Slf4j
@Service
public class GroupeServiceImpl implements GroupeService {

    private final GroupeRepository repository;
    private final GroupeMapper groupeMapper;

    public GroupeServiceImpl(GroupeRepository repository, GroupeMapper groupeMapper) {
        this.repository = repository;
        this.groupeMapper = groupeMapper;
    }

    @Override
    @Transactional
    public GroupeResponse save(GroupeRequest request) {
        log.info("Saving Groupe with payload: "+request.toString());
        Groupe groupe = groupeMapper.toEntity(request);
        GroupeResponse groupeResponse = groupeMapper.toDto(repository.save(groupe));
        log.info("Groupe is created with id: {}", groupe.getId());
        log.info("Returning GroupeResponse with trackingId: {}", groupeResponse.getTrackingId());
        return groupeResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public GroupeResponse getOne(UUID id) {
        log.info("Searching for Groupe with id: "+id);
        Groupe groupe = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("Groupe with id: "+id+" not found!"));
        GroupeResponse groupeResponse = groupeMapper.toDto(groupe);
        log.info("Groupe is found with id: {}", groupe.getId());
        log.info("Returning GroupeResponse with trackingId: {}", groupeResponse.getTrackingId());
        return groupeResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public GroupeResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for Groupe with trackingId: "+trackingId);
        Groupe groupe = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Groupe with trackingId: "+trackingId+" not found!"));
        GroupeResponse groupeResponse = groupeMapper.toDto(groupe);
        log.info("Groupe is found with trackingId: {}", groupe.getTrackingId());
        log.info("Returning GroupeResponse with trackingId: {}", groupeResponse.getTrackingId());
        return groupeResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupeResponse> getAll(int page, int size, boolean deleted) {
        log.info("Searching for Groupe list with (page, size, deleted) : "+"("+page+", "+size+", "+deleted+")");
        Page<Groupe> groupePage = repository.findAllByDeleted(deleted, PageRequest.of(page, size));
        Page<GroupeResponse> groupePageResponse = groupePage.map(groupeMapper::toDto);
        log.info("Groupe list found with size: {}", groupePage.getTotalElements());
        log.info("Returning GroupeResponse list with size: {}", groupePage.getTotalElements());
        return groupePageResponse;
    }

    @Override
    @Transactional
    public GroupeResponse updateOne(GroupeRequest request) {
        log.info("Updating Groupe with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("Groupe trackingId should not be null !");

        Groupe groupe = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Groupe with id: "+request.getTrackingId()+" not found!"));
        groupe.update(request);
        GroupeResponse groupeResponse = groupeMapper.toDto(repository.save(groupe));
        log.info("Groupe is updated with id: {}", groupe.getId());
        log.info("Returning GroupeResponse with trackingId: {}", groupeResponse.getTrackingId());
        return groupeResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting Groupe with trackingId:: "+trackingId);
        Groupe groupe = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Groupe with id: "+trackingId+" not found!"));
        groupe.delete();
        repository.save(groupe);
        log.info("Groupe is deleted with id: {}", groupe.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countByDeletedFalse();
        log.info("Groupe total count : {}", count);
        return count;
    }
}
