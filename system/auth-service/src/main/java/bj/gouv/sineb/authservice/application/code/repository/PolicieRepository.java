package bj.gouv.sineb.authservice.application.code.repository;

import bj.gouv.sineb.authservice.application.code.entity.Policie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PolicieRepository extends RevisionRepository<Policie, UUID, Long>, JpaRepository<Policie, UUID> {

    long countByDeletedFalse();
    long countByDeletedFalseAndExpiredFalse();

    Optional<Policie> findByTrackingId(UUID trackingId);
    Optional<Policie> findByDeletedFalseAndUserIdAndRessourceIdAndScopeId(UUID userId, UUID ressourceId, UUID scopeId);
    Optional<Policie> findByDeletedFalseAndUserTrackingIdAndRessourceTrackingIdAndScopeTrackingId(UUID userTrackingId, UUID ressourceTrackingId, UUID scopeTrackingId);
    Page<Policie> findAllByDeletedAndExpired(boolean deleted, boolean expired, Pageable pageable);
    List<Policie> findAllByDeletedAndExpired(boolean deleted, boolean expired);
    List<Policie> findAllByDeletedFalseAndExpiredFalseAndUserId(UUID userId);
    List<Policie> findAllByDeletedFalseAndUserIdAndRessourceId(UUID userId, UUID ressourceId);

    long countByDeletedFalseOrExpiredFalse();
}
