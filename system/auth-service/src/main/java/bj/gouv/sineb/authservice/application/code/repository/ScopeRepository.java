package bj.gouv.sineb.authservice.application.code.repository;

import bj.gouv.sineb.authservice.application.code.entity.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScopeRepository extends RevisionRepository<Scope, UUID, Long>, JpaRepository<Scope, UUID> {
    long countByDeletedFalse();
    Optional<Scope> findByTrackingId(UUID trackingId);
    Page<Scope> findAllByDeleted(boolean deleted, Pageable pageable);
}
