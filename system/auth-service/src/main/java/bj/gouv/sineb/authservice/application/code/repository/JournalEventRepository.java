package bj.gouv.sineb.authservice.application.code.repository;

import bj.gouv.sineb.authservice.application.code.entity.JournalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JournalEventRepository extends JpaRepository<JournalEvent, UUID> {

    Optional<JournalEvent> findByTrackingId(UUID trackingId);
}
