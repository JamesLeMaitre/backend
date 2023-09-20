package bj.gouv.sineb.demandeservice.application.code.repository.common;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.demandeservice.application.code.entity.common.Civilite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CiviliteRepository extends JpaRepository<Civilite, UUID> {

    Optional<Civilite> findCiviliteByDataStatusIsNotAndTrackingId(DataStatus dataStatus, UUID trackingId);

    Page<Civilite> findCiviliteByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);

}
