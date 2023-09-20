package bj.gouv.sineb.authservice.application.code.service.impl;

import bj.gouv.sineb.authservice.application.code.entity.Role;
import bj.gouv.sineb.authservice.application.code.dto.request.RoleRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.RoleResponse;
import bj.gouv.sineb.authservice.application.code.mapper.RoleMapper;
import bj.gouv.sineb.authservice.application.code.repository.RoleRepository;
import bj.gouv.sineb.authservice.application.code.service.RoleService;
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
public class RoleServiceImpl implements RoleService {

    private final  RoleRepository repository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository repository, RoleMapper roleMapper) {
        this.repository = repository;
        this.roleMapper = roleMapper;
    }


    @Override
    @Transactional
    public RoleResponse save(RoleRequest request) {
        log.info("Saving Role with payload: "+request.toString());
        Role role = roleMapper.toEntity(request);
        RoleResponse roleResponse = roleMapper.toDto(repository.save(role));
        log.info("Role is created with id: {}", role.getId());
        log.info("Returning RoleResponse with trackingId: {}", roleResponse.getTrackingId());
        return roleResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getOne(UUID id) {
        log.info("Searching for Role with id: "+id);
        Role role = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("Role with id: "+id+" not found!"));
        RoleResponse roleResponse = roleMapper.toDto(role);
        log.info("Role is found with id: {}", role.getId());
        log.info("Returning RoleResponse with trackingId: {}", roleResponse.getTrackingId());
        return roleResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for Role with trackingId: "+trackingId);
        Role role = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Role with trackingId: "+trackingId+" not found!"));
        RoleResponse roleResponse = roleMapper.toDto(role);
        log.info("Role is found with trackingId: {}", role.getTrackingId());
        log.info("Returning RoleResponse with trackingId: {}", roleResponse.getTrackingId());
        return roleResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleResponse> getAll(int page, int size, boolean deleted) {
        log.info("Searching for Role list with (page, size, deleted) : "+"("+page+", "+size+", "+deleted+")");
        Page<Role> rolePage = repository.findAllByDeleted(deleted, PageRequest.of(page, size));
        Page<RoleResponse> rolePageResponse = rolePage.map(roleMapper::toDto);
        log.info("Role list found with size: {}", rolePage.getTotalElements());
        log.info("Returning RoleResponse list with size: {}", rolePage.getTotalElements());
        return rolePageResponse;
    }

    @Override
    @Transactional
    public RoleResponse updateOne(RoleRequest request) {
        if (request.getTrackingId()==null)
            throw new CustomException("Role trackingId should not be null !");
        log.info("Updating Role with payload: "+request.toString());
        Role role = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Role with id: "+request.getTrackingId()+" not found!"));
        role.update(request);
        RoleResponse roleResponse = roleMapper.toDto(repository.save(role));
        log.info("Role is updated with id: {}", role.getId());
        log.info("Returning RoleResponse with trackingId: {}", roleResponse.getTrackingId());
        return roleResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting Role with trackingId:: "+trackingId);
        Role role = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Role with id: "+trackingId+" not found!"));
        role.delete();
        repository.save(role);
        log.info("Role is deleted with id: {}", role.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countByDeletedFalse();
        log.info("Role total count : {}", count);
        return count;
    }

}
