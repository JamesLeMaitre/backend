package bj.gouv.sineb.authservice.application.code.repository;

import bj.gouv.sineb.authservice.application.code.entity.SystemVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SystemVariableRepository extends RevisionRepository<SystemVariable, UUID, Long>, JpaRepository<SystemVariable, UUID> {
    long countByDeletedFalse();
    Optional<SystemVariable> findByTrackingId(UUID trackingId);
    List<SystemVariable> findAllByDeleted(boolean deleted);
    Page<SystemVariable> findAllByDeleted(boolean deleted, Pageable pageable);
}
