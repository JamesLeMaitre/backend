package bj.gouv.sineb.authservice.application.code.service.impl;

import bj.gouv.sineb.authservice.application.code.entity.GroupeUser;
import bj.gouv.sineb.authservice.application.code.helper.GroupeUserHelper;
import bj.gouv.sineb.authservice.application.code.mapper.GroupeUserMapper;
import bj.gouv.sineb.authservice.application.code.dto.request.GroupeUserRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.GroupeUserResponse;
import bj.gouv.sineb.authservice.application.code.repository.GroupeUserRepository;
import bj.gouv.sineb.authservice.application.code.service.GroupeUserService;
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
public class GroupeUserServiceImpl implements GroupeUserService {

    private final GroupeUserRepository repository;
    private final GroupeUserMapper groupeUserMapper;
    private final GroupeUserHelper groupeUserHelper;

    public GroupeUserServiceImpl(GroupeUserRepository repository,
                                 GroupeUserMapper groupeUserMapper,
                                 GroupeUserHelper groupeUserHelper) {
        this.repository = repository;
        this.groupeUserMapper = groupeUserMapper;
        this.groupeUserHelper = groupeUserHelper;
    }

    @Override
    @Transactional
    public GroupeUserResponse save(GroupeUserRequest request) {
        log.info("Saving GroupeUser with payload: "+request.toString());
        GroupeUser groupeUser = groupeUserMapper.toEntity(request);
        GroupeUserResponse groupeUserResponse = groupeUserMapper.toDto(repository.save(groupeUser));
        log.info("GroupeUser is created with id: {}", groupeUser.getId());
        log.info("Returning GroupeUserResponse with trackingId: {}", groupeUserResponse.getTrackingId());
        return groupeUserResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public GroupeUserResponse getOne(UUID id) {
        log.info("Searching for GroupeUser with id: "+id);
        GroupeUser groupeUser = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("GroupeUser with id: "+id+" not found!"));
        GroupeUserResponse groupeUserResponse = groupeUserMapper.toDto(groupeUser);
        log.info("GroupeUser is found with id: {}", groupeUser.getId());
        log.info("Returning GroupeUserResponse with trackingId: {}", groupeUserResponse.getTrackingId());
        return groupeUserResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public GroupeUserResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for GroupeUser with trackingId: "+trackingId);
        GroupeUser groupeUser = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("GroupeUser with trackingId: "+trackingId+" not found!"));
        GroupeUserResponse groupeUserResponse = groupeUserMapper.toDto(groupeUser);
        log.info("GroupeUser is found with trackingId: {}", groupeUser.getTrackingId());
        log.info("Returning GroupeUserResponse with trackingId: {}", groupeUserResponse.getTrackingId());
        return groupeUserResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupeUserResponse> getAllByUserId(UUID userTrackingId, int page, int size, boolean deleted) {
        log.info("Searching for GroupeUser list by email with (page, size, deleted) : "+"("+page+", "+size+", "+deleted+")");
        Page<GroupeUser> groupeUserPage = repository.findAllByDeletedAndUserTrackingId(deleted, userTrackingId, PageRequest.of(page, size));
        Page<GroupeUserResponse> groupeUserPageResponse = groupeUserPage.map(groupeUserMapper::toDto);
        log.info("GroupeUser list by ressource found with size: {}", groupeUserPage.getTotalElements());
        log.info("Returning GroupeUserResponse list by ressource with size: {}", groupeUserPage.getTotalElements());
        return groupeUserPageResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupeUserResponse> getAll(int page, int size, boolean deleted) {
        log.info("Searching for GroupeUser list with (page, size, deleted) : "+"("+page+", "+size+", "+deleted+")");
        Page<GroupeUser> groupeUserPage = repository.findAllByDeleted(deleted, PageRequest.of(page, size));
        Page<GroupeUserResponse> groupeUserPageResponse = groupeUserPage.map(groupeUserMapper::toDto);
        log.info("GroupeUser list found with size: {}", groupeUserPage.getTotalElements());
        log.info("Returning GroupeUserResponse list with size: {}", groupeUserPage.getTotalElements());
        return groupeUserPageResponse;
    }

    @Override
    @Transactional
    public GroupeUserResponse updateOne(GroupeUserRequest request) {
        log.info("Updating GroupeUser with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("GroupeUser trackingId should not be null !");

        GroupeUser groupeUser = groupeUserHelper.update(request);
        GroupeUserResponse groupeUserResponse = groupeUserMapper.toDto(repository.save(groupeUser));
        log.info("GroupeUser is updated with id: {}", groupeUser.getId());
        log.info("Returning GroupeUserResponse with trackingId: {}", groupeUserResponse.getTrackingId());
        return groupeUserResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting GroupeUser with trackingId:: "+trackingId);
        GroupeUser groupeUser = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("GroupeUser with id: "+trackingId+" not found!"));
        groupeUser.delete();
        repository.save(groupeUser);
        log.info("GroupeUser is deleted with id: {}", groupeUser.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countByDeletedFalse();
        log.info("GroupeUser total count : {}", count);
        return count;
    }
}
