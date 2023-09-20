package bj.gouv.sineb.ddbservice.application.code.repository.sie;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.sie.BlocActiviteEconomique;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlocActiviteEconomiqueRepository extends RevisionRepository<BlocActiviteEconomique, UUID, Long>, JpaRepository<BlocActiviteEconomique, UUID> {

    Page<BlocActiviteEconomique> findAllByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);
    Optional<BlocActiviteEconomique> findByTrackingId(UUID trackingId);
    long countAllByDataStatusIsNot(DataStatus dataStatus);
}
