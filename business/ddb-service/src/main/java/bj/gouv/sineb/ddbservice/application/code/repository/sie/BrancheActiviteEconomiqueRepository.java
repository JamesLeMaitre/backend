package bj.gouv.sineb.ddbservice.application.code.repository.sie;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.sie.BrancheActiviteEconomique;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrancheActiviteEconomiqueRepository extends RevisionRepository<BrancheActiviteEconomique, UUID, Long>, JpaRepository<BrancheActiviteEconomique, UUID> {

    Page<BrancheActiviteEconomique> findAllByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);
    Page<BrancheActiviteEconomique> findAllByDataStatusIsNotAndBlocActiviteEconomiqueTrackingId(DataStatus dataStatus, UUID BlocActiviteEconomiqueTrackingId, Pageable pageable);
    Optional<BrancheActiviteEconomique> findByTrackingId(UUID trackingId);
    long countAllByDataStatusIsNot(DataStatus dataStatus);

}
