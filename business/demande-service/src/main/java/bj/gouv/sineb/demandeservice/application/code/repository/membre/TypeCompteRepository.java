package bj.gouv.sineb.demandeservice.application.code.repository.membre;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.demandeservice.application.code.entity.membre.TypeCompte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TypeCompteRepository extends JpaRepository<TypeCompte, UUID> {

    Optional<TypeCompte> findByDataStatusIsNotAndTrackingId(DataStatus dataStatus, UUID trackingId);

    Page<TypeCompte> findTypeCompteByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);

}
