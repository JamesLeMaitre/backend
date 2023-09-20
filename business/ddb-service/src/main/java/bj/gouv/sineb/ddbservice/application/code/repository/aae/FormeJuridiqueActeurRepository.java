package bj.gouv.sineb.ddbservice.application.code.repository.aae;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.FormeJuridiqueActeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormeJuridiqueActeurRepository extends RevisionRepository<FormeJuridiqueActeur, UUID, Long>, JpaRepository<FormeJuridiqueActeur, UUID> {

    Page<FormeJuridiqueActeur> findAllByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);
    Optional<FormeJuridiqueActeur> findByTrackingId(UUID trackingId);
    long countAllByDataStatusIsNot(DataStatus dataStatus);
}
