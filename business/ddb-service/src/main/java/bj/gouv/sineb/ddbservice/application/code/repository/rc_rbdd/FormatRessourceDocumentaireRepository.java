package bj.gouv.sineb.ddbservice.application.code.repository.rc_rbdd;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.rc_rbdd.FormatRessourceDocumentaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormatRessourceDocumentaireRepository extends RevisionRepository<FormatRessourceDocumentaire, UUID, Long>, JpaRepository<FormatRessourceDocumentaire, UUID> {

    Page<FormatRessourceDocumentaire> findAllByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);
    Optional<FormatRessourceDocumentaire> findByTrackingId(UUID trackingId);
    long countAllByDataStatusIsNot(DataStatus dataStatus);

}
