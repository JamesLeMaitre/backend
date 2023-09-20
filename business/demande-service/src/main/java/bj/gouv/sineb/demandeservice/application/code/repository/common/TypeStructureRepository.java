package bj.gouv.sineb.demandeservice.application.code.repository.common;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.demandeservice.application.code.entity.common.TypeStructure;
import bj.gouv.sineb.demandeservice.application.code.entity.common.TypeStructure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TypeStructureRepository extends JpaRepository<TypeStructure, UUID> {

    Optional<TypeStructure> findByDataStatusIsNotAndTrackingId(DataStatus dataStatus, UUID trackingId);

    Page<TypeStructure> findTypeStructureByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);


}
