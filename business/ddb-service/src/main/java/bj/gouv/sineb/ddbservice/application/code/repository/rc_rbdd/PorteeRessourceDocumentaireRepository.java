package bj.gouv.sineb.ddbservice.application.code.repository.rc_rbdd;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.PorteeRessourceDocumentaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PorteeRessourceDocumentaireRepository extends RevisionRepository<PorteeRessourceDocumentaire, UUID, Long>, JpaRepository<PorteeRessourceDocumentaire, UUID> {

    Page<PorteeRessourceDocumentaire> findAllByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);
    Optional<PorteeRessourceDocumentaire> findByTrackingId(UUID trackingId);
    long countAllByDataStatusIsNot(DataStatus dataStatus);

}
