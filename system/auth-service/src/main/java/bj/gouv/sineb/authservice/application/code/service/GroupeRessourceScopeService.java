package bj.gouv.sineb.authservice.application.code.service;

import bj.gouv.sineb.authservice.application.code.dto.request.GroupeRessourceScopeRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.GroupeRessourceScopeResponse;
import bj.gouv.sineb.authservice.application.code.entity.GroupeRessourceScope;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface GroupeRessourceScopeService {
    void save(@Valid GroupeRessourceScopeRequest request);
    GroupeRessourceScopeResponse getOneGroupeRessourcesAndScopes(boolean deleted, UUID groupeTrackingId);
    GroupeRessourceScope getOne(UUID id);
    List<GroupeRessourceScopeResponse> getAllGroupesRessourcesAndScopes(boolean deleted);
    List<GroupeRessourceScope> getAll(int page, int size, boolean deleted);
    List<String> getUserScopesListed(UUID userTrackingId);
    List<String> buildUserScopeListNowFromGroupeRessourceScopes(List<GroupeRessourceScopeResponse> groupeRessourceScopeResponseList);
    void addSpecificScope(UUID groupeTrackingId, UUID ressourceTrackingId, UUID scopeTrackingId);
    void deleteOne(UUID groupeTrackingId, UUID ressourceTrackingId, UUID scopeTrackingId);
    long count();
}
