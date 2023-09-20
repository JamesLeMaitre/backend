package bj.gouv.sineb.ddbservice.application.code.repository.common;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.common.FormeEnergieSecondaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormeEnergieSecondaireRepository extends RevisionRepository<FormeEnergieSecondaire, UUID, Long>, JpaRepository<FormeEnergieSecondaire, UUID> {

    Page<FormeEnergieSecondaire> findAllByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);
    Page<FormeEnergieSecondaire> findAllByDeletedAndFormeEnergiePrimaireTrackingId(boolean deleted, UUID formeEnergiePrimaireTrackingId, Pageable pageable);
    Page<FormeEnergieSecondaire> findAllByDataStatusIsNotAndAndFormeEnergiePrimaireTrackingId(DataStatus dataStatus, UUID formeEnergiePrimaireTrackingId, Pageable pageable);

    Optional<FormeEnergieSecondaire> findByTrackingId(UUID trackingId);
    long countAllByDataStatusIsNot(DataStatus dataStatus);

}
