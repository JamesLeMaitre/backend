package bj.gouv.sineb.demandeservice.application.code.repository.common;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.demandeservice.application.code.entity.common.TypeDemande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TypeDemandeRepository extends JpaRepository<TypeDemande, UUID> {

    Optional<TypeDemande> findByDataStatusIsNotAndTrackingId(DataStatus dataStatus, UUID trackingId);

    Page<TypeDemande> findTypeDemandeByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);

}
