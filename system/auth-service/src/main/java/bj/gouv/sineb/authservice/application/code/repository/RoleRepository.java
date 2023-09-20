package bj.gouv.sineb.authservice.application.code.repository;

import bj.gouv.sineb.authservice.application.code.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends RevisionRepository<Role, UUID, Long>, JpaRepository<Role, UUID> {
    long countByDeletedFalse();
    Optional<Role> findByTrackingId(UUID trackingId);
    Page<Role> findAllByDeleted(boolean deleted, Pageable pageable);
}
