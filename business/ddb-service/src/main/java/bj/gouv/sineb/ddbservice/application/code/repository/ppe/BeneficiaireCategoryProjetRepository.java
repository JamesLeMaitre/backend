package bj.gouv.sineb.ddbservice.application.code.repository.ppe;


import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.ppe.BeneficiaireCategoryProjet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BeneficiaireCategoryProjetRepository extends RevisionRepository<BeneficiaireCategoryProjet, UUID, Long>, JpaRepository<BeneficiaireCategoryProjet, UUID> {

    Page<BeneficiaireCategoryProjet> findAllByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);
    Optional<BeneficiaireCategoryProjet> findByTrackingId(UUID trackingId);
    long countAllByDataStatusIsNot(DataStatus dataStatus);

}
