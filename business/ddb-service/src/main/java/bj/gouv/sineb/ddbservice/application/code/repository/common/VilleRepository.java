package bj.gouv.sineb.ddbservice.application.code.repository.common;


import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VilleRepository extends RevisionRepository<Ville, UUID, Long>, JpaRepository<Ville, UUID> {

    Page<Ville> findAllByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);
    Optional<Ville> findByTrackingId(UUID trackingId);
    long countAllByDataStatusIsNot(DataStatus dataStatus);

}
