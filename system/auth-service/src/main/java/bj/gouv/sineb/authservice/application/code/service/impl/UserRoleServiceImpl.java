package bj.gouv.sineb.authservice.application.code.service.impl;

import bj.gouv.sineb.authservice.application.code.entity.UserRole;
import bj.gouv.sineb.authservice.application.code.helper.UserRoleHelper;
import bj.gouv.sineb.authservice.application.code.dto.request.UserRoleRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.UserRoleResponse;
import bj.gouv.sineb.authservice.application.code.mapper.UserRoleMapper;
import bj.gouv.sineb.authservice.application.code.repository.UserRoleRepository;
import bj.gouv.sineb.authservice.application.code.service.UserRoleService;
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
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository repository;
    private final UserRoleMapper userRoleMapper;
    private final UserRoleHelper userRoleHelper;



    public UserRoleServiceImpl(UserRoleRepository repository,
                               UserRoleMapper userRoleMapper,
                               UserRoleHelper userRoleHelper) {
        this.repository = repository;
        this.userRoleMapper = userRoleMapper;
        this.userRoleHelper = userRoleHelper;
    }

    @Override
    @Transactional
    public UserRoleResponse save(UserRoleRequest request) {
        log.info("Saving UserRole with payload: "+request.toString());
        UserRole userRole = userRoleMapper.toEntity(request);
        log.info("Deleting email active role ...");
        userRoleHelper.disableUserActiveRole(request);
        UserRoleResponse userRoleResponse = userRoleMapper.toDto(repository.save(userRole));
        log.info("UserRole is created with id: {}", userRole.getId());
        log.info("Returning UserRoleResponse with trackingId: {}", userRoleResponse.getTrackingId());
        return userRoleResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public UserRoleResponse getOne(UUID id) {
        log.info("Searching for UserRole with id: "+id);
        UserRole userRole = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("UserRole with id: "+id+" not found!"));
        UserRoleResponse userRoleResponse = userRoleMapper.toDto(userRole);
        log.info("UserRole is found with id: {}", userRole.getId());
        log.info("Returning UserRoleResponse with trackingId: {}", userRoleResponse.getTrackingId());
        return userRoleResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public UserRoleResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for UserRole with trackingId: "+trackingId);
        UserRole userRole = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("UserRole with trackingId: "+trackingId+" not found!"));
        UserRoleResponse userRoleResponse = userRoleMapper.toDto(userRole);
        log.info("UserRole is found with trackingId: {}", userRole.getTrackingId());
        log.info("Returning UserRoleResponse with trackingId: {}", userRoleResponse.getTrackingId());
        return userRoleResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserRoleResponse> getAllByRoleId(UUID roleId, int page, int size, boolean active) {
        log.info("Searching for UserRole list by ressource with (page, size, deleted) : "+"("+page+", "+size+", "+active+")");
        Page<UserRole> userRolePage = repository.findAllByActiveAndRoleTrackingId(active, roleId, PageRequest.of(page, size));
        Page<UserRoleResponse> userRolePageResponse = userRolePage.map(userRoleMapper::toDto);
        log.info("UserRole list by ressource found with size: {}", userRolePage.getTotalElements());
        log.info("Returning UserRoleResponse list by ressource with size: {}", userRolePage.getTotalElements());
        return userRolePageResponse;
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public Page<UserRoleResponse> getAll(int page, int size, boolean active) {
        log.info("Searching for UserRole list with (page, size, deleted) : "+"("+page+", "+size+", "+active+")");
        Page<UserRole> userRolePage = repository.findAllByActive(active, PageRequest.of(page, size));
        Page<UserRoleResponse> userRolePageResponse = userRolePage.map(userRoleMapper::toDto);
        log.info("UserRole list found with size: {}", userRolePage.getTotalElements());
        log.info("Returning UserRoleResponse list with size: {}", userRolePage.getTotalElements());
        return userRolePageResponse;
    }

    @Override
    @Transactional
    public UserRoleResponse updateOne(UserRoleRequest request) {
        log.info("Updating UserRole with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("UserRole trackingId should not be null !");

        UserRole userRole = userRoleHelper.update(request);
        UserRoleResponse userRoleResponse = userRoleMapper.toDto(repository.save(userRole));
        log.info("UserRole is updated with id: {}", userRole.getId());
        log.info("Returning UserRoleResponse with trackingId: {}", userRoleResponse.getTrackingId());
        return userRoleResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting UserRole with trackingId:: "+trackingId);
        UserRole userRole = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("UserRole with id: "+trackingId+" not found!"));
        userRole.delete();
        repository.save(userRole);
        log.info("UserRole is deleted with id: {}", userRole.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countByActiveTrue();
        log.info("UserRole total count : {}", count);
        return count;
    }

}
