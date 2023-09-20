package bj.gouv.sineb.authservice.application.code.repository;

import bj.gouv.sineb.authservice.application.code.entity.GroupeUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupeUserRepository extends RevisionRepository<GroupeUser, UUID, Long>, JpaRepository<GroupeUser, UUID> {
    long countByDeletedFalse();
    Optional<GroupeUser> findByTrackingId(UUID trackingId);

    Page<GroupeUser> findAllByDeleted(boolean deleted, Pageable pageable);
    List<GroupeUser> findAllByDeletedFalseAndUserId(UUID userId);
    List<GroupeUser> findAllByDeletedAndUserId(boolean deleted, UUID userId);
    Page<GroupeUser> findAllByDeletedAndUserTrackingId(boolean deleted, UUID userTrackingId, Pageable pageable);
    Page<GroupeUser> findAllByDeletedAndGroupeId(boolean deleted,UUID groupeId, Pageable pageable);
    List<GroupeUser> findAllByDeletedAndGroupeId(boolean deleted,UUID groupeId);
    List<GroupeUser> findAllByDeletedAndGroupeIdAndUserId(boolean deleted,UUID groupeId, UUID userId);
}
