package bj.gouv.sineb.authservice.application.code.repository;

import bj.gouv.sineb.authservice.application.code.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends RevisionRepository<UserRole, UUID, Long>, JpaRepository<UserRole, UUID> {
    long countByActiveTrue();

    Optional<UserRole> findByTrackingId(UUID trackingId);

    Page<UserRole> findAllByActive(boolean active, Pageable pageable);

    Page<UserRole> findAllByActiveAndRoleTrackingId(boolean active, UUID roleId, Pageable pageable);

    List<UserRole> findAllByActiveAndUserIdAndRoleId(boolean deleted, UUID userId, UUID roleId);

    List<UserRole> findAllByActiveTrueAndUserIdAndRoleId(UUID userId, UUID roleId);
    List<UserRole> findAllByActiveAndUserId(boolean active, UUID userId);

}
