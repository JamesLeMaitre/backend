package bj.gouv.sineb.authservice.application.code.repository;

import bj.gouv.sineb.authservice.application.code.entity.GroupeRessourceScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupeRessourceScopeRepository extends RevisionRepository<GroupeRessourceScope, UUID, Long>, JpaRepository<GroupeRessourceScope, UUID>{

    long countByDeletedFalse();

    Optional<GroupeRessourceScope> findByTrackingId(UUID trackingId);
    Optional<GroupeRessourceScope> findByDeletedFalseAndGroupeTrackingIdAndRessourceTrackingIdAndScopeTrackingId(UUID groupeTrackingId, UUID ressourceTrackingId, UUID scopeTrackingId);
    Optional<GroupeRessourceScope> findFirstByGroupeIdAndRessourceId(UUID groupeId, UUID ressourceId);

    List<GroupeRessourceScope> findAllByDeleted(boolean deleted, Pageable pageable);
    List<GroupeRessourceScope> findAllByDeletedAndGroupeId(boolean deleted, UUID groupeTrackingId);
    List<GroupeRessourceScope> findAllByDeletedFalseAndGroupeId(UUID groupeId);
    List<GroupeRessourceScope> findAllByDeletedFalseAndGroupeIdAndRessourceId(UUID groupeId, UUID ressourceId);
    Page<GroupeRessourceScope> findAllByDeletedAndRessourceId(boolean deleted, UUID ressourceId, Pageable pageable);
}
