package bj.gouv.sineb.authservice.application.code.helper;

import bj.gouv.sineb.authservice.application.code.dto.request.GroupeUserRequest;
import bj.gouv.sineb.authservice.application.code.entity.Groupe;
import bj.gouv.sineb.authservice.application.code.entity.GroupeUser;
import bj.gouv.sineb.authservice.application.code.entity.User;
import bj.gouv.sineb.authservice.application.code.repository.GroupeRepository;
import bj.gouv.sineb.authservice.application.code.repository.GroupeUserRepository;
import bj.gouv.sineb.authservice.application.code.repository.UserRepository;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class GroupeUserHelper {

    private final GroupeUserRepository groupeUserRepository;
    private final GroupeRepository groupeRepository;
    private final UserRepository userRepository;

    public GroupeUserHelper(GroupeUserRepository groupeUserRepository, GroupeRepository groupeRepository, UserRepository userRepository) {
        this.groupeUserRepository = groupeUserRepository;
        this.groupeRepository = groupeRepository;
        this.userRepository = userRepository;
    }

    public GroupeUser update(GroupeUserRequest request) {
        GroupeUser groupeUser = groupeUserRepository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("GroupeRole with trackingId: "+request.getTrackingId()+" not found!"));
        Groupe groupe = groupeRepository.findByTrackingId(request.getGroupeTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Groupe with trackingId: "+request.getGroupeTrackingId()+" not found!"));
        User user = userRepository.findByTrackingId(request.getUserTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("User with trackingId: "+request.getUserTrackingId()+" not found!"));

        groupeUser.setGroupe(groupe);
        groupeUser.setUser(user);

        return groupeUser;
    }
}
