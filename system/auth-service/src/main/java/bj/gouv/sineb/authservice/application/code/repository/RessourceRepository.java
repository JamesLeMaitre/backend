package bj.gouv.sineb.authservice.application.code.repository;

import bj.gouv.sineb.authservice.application.code.entity.Ressource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RessourceRepository extends RevisionRepository<Ressource, UUID, Long>, JpaRepository<Ressource, UUID> {

    long countByDeletedFalse();

    Optional<Ressource> findByTrackingId(UUID trackingId);

    Page<Ressource> findAllByDeleted(boolean deleted, Pageable pageable);
}
