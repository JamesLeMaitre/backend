package bj.gouv.sineb.demandeservice.application.code.repository.membre;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.demandeservice.application.code.entity.membre.DemandeCompte;
import bj.gouv.sineb.demandeservice.application.code.entity.membre.DemandeCompte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DemandeCompteRepository extends JpaRepository<DemandeCompte, UUID> {

    Optional<DemandeCompte> findByDataStatusIsNotAndTrackingId(DataStatus dataStatus, UUID trackingId);

    Page<DemandeCompte> findDemandeCompteByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);

    Page<DemandeCompte> findDemandeCompteByDataStatusIsNotAndTypeCompte(DataStatus dataStatus, UUID typeCompteId, Pageable pageable);

    Page<DemandeCompte> findDemandeCompteByDataStatusIsNotAndTypeDemande(DataStatus dataStatus, UUID typeDemandeId, Pageable pageable);

    Page<DemandeCompte> findDemandeCompteByDataStatusIsNotAndCivilite(DataStatus dataStatus, UUID civiliteId, Pageable pageable);

    Page<DemandeCompte> findDemandeCompteByDataStatusIsNotAndPays(DataStatus dataStatus, UUID paysId, Pageable pageable);

    Optional<DemandeCompte> findByDataStatusIsNotAndMail(DataStatus dataStatus, String email);

}
