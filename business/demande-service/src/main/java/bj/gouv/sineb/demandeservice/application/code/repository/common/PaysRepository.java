package bj.gouv.sineb.demandeservice.application.code.repository.common;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.demandeservice.application.code.entity.common.Pays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaysRepository extends JpaRepository<Pays, UUID> {

    Optional<Pays> findByDataStatusIsNotAndTrackingId(DataStatus dataStatus, UUID trackingId);

    Page<Pays> findPaysByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);

}
