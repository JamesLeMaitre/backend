package bj.gouv.sineb.ddbservice.application.code.repository.aae;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.aae.RoleActeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleActeurRepository extends RevisionRepository<RoleActeur, UUID, Long>, JpaRepository<RoleActeur, UUID> {

    Page<RoleActeur> findAllByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);
    Optional<RoleActeur> findByTrackingId(UUID trackingId);
    long countAllByDataStatusIsNot(DataStatus dataStatus);

}
