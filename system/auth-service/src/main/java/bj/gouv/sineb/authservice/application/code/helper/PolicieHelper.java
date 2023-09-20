package bj.gouv.sineb.authservice.application.code.helper;

import bj.gouv.sineb.authservice.application.code.entity.Policie;
import bj.gouv.sineb.authservice.application.code.entity.Ressource;
import bj.gouv.sineb.authservice.application.code.entity.Scope;
import bj.gouv.sineb.authservice.application.code.entity.User;
import bj.gouv.sineb.authservice.application.code.repository.PolicieRepository;
import bj.gouv.sineb.authservice.application.code.repository.RessourceRepository;
import bj.gouv.sineb.authservice.application.code.repository.ScopeRepository;
import bj.gouv.sineb.authservice.application.code.repository.UserRepository;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PolicieHelper {

    private final RessourceRepository ressourceRepository;
    private final ScopeRepository scopeRepository;
    private final UserRepository userRepository;
    private final PolicieRepository policieRepository;

    public PolicieHelper(RessourceRepository ressourceRepository,
                         ScopeRepository scopeRepository,
                         UserRepository userRepository,
                         PolicieRepository policieRepository) {
        this.ressourceRepository = ressourceRepository;
        this.scopeRepository = scopeRepository;
        this.userRepository = userRepository;
        this.policieRepository = policieRepository;
    }


    public Policie delete(UUID userTrackingId, UUID ressourceTrackingId, UUID scopeTrackingId) {
        Scope scope = scopeRepository.findByTrackingId(scopeTrackingId)
                .orElseThrow(()-> new CustomNotFoundException("Scope with trackingId: "+scopeTrackingId+" not found!"));
        User user = userRepository.findByTrackingId(userTrackingId)
                .orElseThrow(()-> new CustomNotFoundException("User with trackingId: "+userTrackingId+" not found!"));
        Ressource ressource = ressourceRepository.findByTrackingId(ressourceTrackingId)
                .orElseThrow(()-> new CustomNotFoundException("Ressource with trackingId: "+ressourceTrackingId+" not found!"));

        Policie policie = policieRepository.findByDeletedFalseAndUserIdAndRessourceIdAndScopeId(user.getId(), ressource.getId(), scope.getId())
                .orElseThrow(()-> new CustomNotFoundException("Policie with (userTrackingId, ressourceTrackingId, scopeTrackingId) : " +
                        ""+"("+userTrackingId+", "+ressourceTrackingId+", "+scopeTrackingId+") not found!"));
        policie.delete();
        policieRepository.save(policie);
        return policie;
    }
}
