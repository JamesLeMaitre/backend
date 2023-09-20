package bj.gouv.sineb.ddbservice.application.code.repository.rc_rbdd;


import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.DroitAccesRessourceDocumentaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DroitAccesRessourceDocumentaireRepository extends RevisionRepository<DroitAccesRessourceDocumentaire, UUID, Long>, JpaRepository<DroitAccesRessourceDocumentaire, UUID> {

    Page<DroitAccesRessourceDocumentaire> findAllByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);
    Optional<DroitAccesRessourceDocumentaire> findByTrackingId(UUID trackingId);
    long countAllByDataStatusIsNot(DataStatus dataStatus);

}
