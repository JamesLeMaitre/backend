package bj.gouv.sineb.ddbservice.application.code.repository.common;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.common.TypeCompte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;
import java.util.UUID;

public interface TypeCompteRepository extends RevisionRepository<TypeCompte, UUID, Long>, JpaRepository<TypeCompte, UUID> {

    Optional<TypeCompte> findByDataStatusIsNotAndTrackingId(DataStatus dataStatus, UUID trackingId);

    Page<TypeCompte> findTypeCompteByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);

    long countAllByDataStatusIsNot(DataStatus dataStatus);

}
