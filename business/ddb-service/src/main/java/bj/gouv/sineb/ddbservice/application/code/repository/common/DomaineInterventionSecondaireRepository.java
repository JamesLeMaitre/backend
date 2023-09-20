package bj.gouv.sineb.ddbservice.application.code.repository.common;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.common.DomaineInterventionSecondaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DomaineInterventionSecondaireRepository extends RevisionRepository<DomaineInterventionSecondaire, UUID, Long>, JpaRepository<DomaineInterventionSecondaire, UUID> {

    Page<DomaineInterventionSecondaire> findAllByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);

 Page<DomaineInterventionSecondaire> findAllByDataStatusIsNotAndDomaineInterventionPrincipaleTrackingId(DataStatus dataStatus, UUID domaineInterventionPrincipaleTrackingId, Pageable pageable);

    Optional<DomaineInterventionSecondaire> findByTrackingId(UUID trackingId);
    long countAllByDataStatusIsNot(DataStatus dataStatus);

}
