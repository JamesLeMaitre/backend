package bj.gouv.sineb.authservice.application.code.repository;

import bj.gouv.sineb.authservice.application.code.entity.AppEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppEventRepository extends RevisionRepository<AppEvent, UUID, Long>, JpaRepository<AppEvent, UUID> {
    long countByDeletedFalse();
    Optional<AppEvent> findByTrackingId(UUID trackingId);
    List<AppEvent> findAllByDeleted(boolean deleted);
    Page<AppEvent> findAllByDeleted(boolean deleted, Pageable pageable);

}
