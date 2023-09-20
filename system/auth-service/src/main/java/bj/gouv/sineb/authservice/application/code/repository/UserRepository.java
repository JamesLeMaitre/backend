package bj.gouv.sineb.authservice.application.code.repository;

import bj.gouv.sineb.authservice.application.code.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends RevisionRepository<User, UUID, Long>, JpaRepository<User, UUID> {
    long countByEnabledTrueAndDeletedFalse();

    Optional<User> findByTrackingId(UUID trackingId);
    Page<User> findAllByEnabledAndDeleted(boolean enabled, boolean deleted, Pageable pageable);
    Page<User> findAllByDeletedAndEnabledTrueAndAccessTokenExpiredAtLessThan(boolean deleted, Instant instant, Pageable pageable);
    Optional<User> findByEmail(String email);

    List<User> findRevisionsByEmail(String email);
}
