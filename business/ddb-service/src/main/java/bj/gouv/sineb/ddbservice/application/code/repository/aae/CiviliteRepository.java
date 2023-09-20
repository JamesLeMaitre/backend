package bj.gouv.sineb.ddbservice.application.code.repository.aae;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.Civilite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CiviliteRepository extends RevisionRepository<Civilite, UUID, Long>, JpaRepository<Civilite, UUID> {

//    Page<Civilite> findAllByDeleted(boolean deleted, Pageable pageable);
    Page<Civilite> findByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);
//    Optional<Civilite> findByTrackingId(UUID trackingId);
    Optional<Civilite> findByDataStatusIsNotAndTrackingId(DataStatus dataStatus, UUID trackingId);
//    long countByDeletedFalse();
    long countByDataStatusIsNot(DataStatus dataStatus);
}
