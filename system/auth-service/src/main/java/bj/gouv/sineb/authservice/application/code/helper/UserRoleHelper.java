package bj.gouv.sineb.authservice.application.code.helper;

import bj.gouv.sineb.authservice.application.code.entity.Role;
import bj.gouv.sineb.authservice.application.code.dto.request.UserRoleRequest;
import bj.gouv.sineb.authservice.application.code.entity.User;
import bj.gouv.sineb.authservice.application.code.entity.UserRole;
import bj.gouv.sineb.authservice.application.code.repository.RoleRepository;
import bj.gouv.sineb.authservice.application.code.repository.UserRepository;
import bj.gouv.sineb.authservice.application.code.repository.UserRoleRepository;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRoleHelper {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleHelper(UserRoleRepository userRoleRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserRole update(UserRoleRequest request) {
        UserRole userRole = userRoleRepository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("UserRole with trackingId: "+request.getTrackingId()+" not found!"));
        User user = userRepository.findByTrackingId(request.getUserTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("User with trackingId: "+request.getUserTrackingId()+" not found!"));
        Role role = roleRepository.findByTrackingId(request.getRoleTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Role with trackingId: "+request.getRoleTrackingId()+" not found!"));

        userRole.setUser(user);
        userRole.setRole(role);

        return userRole;
    }

    public void disableUserActiveRole(UserRoleRequest request) {
        User user = userRepository.findByTrackingId(request.getUserTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("User with trackingId: "+request.getUserTrackingId()+" not found!"));
        Role role = roleRepository.findByTrackingId(request.getRoleTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Role with trackingId: "+request.getRoleTrackingId()+" not found!"));

        List<UserRole> userRoleList = userRoleRepository.findAllByActiveTrueAndUserIdAndRoleId(user.getId(), role.getId());
        userRoleList.forEach(userRole -> userRole.setActive(false));
        userRoleRepository.saveAll(userRoleList);
    }
}
