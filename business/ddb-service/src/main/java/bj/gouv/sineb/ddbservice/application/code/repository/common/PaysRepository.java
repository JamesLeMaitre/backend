package bj.gouv.sineb.ddbservice.application.code.repository.common;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.common.Pays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaysRepository extends RevisionRepository<Pays, UUID, Long>, JpaRepository<Pays, UUID> {

    Page<Pays> findByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);

    Optional<Pays> findByDataStatusIsNotAndTrackingId(DataStatus dataStatus, UUID trackingId);

    long countByDataStatusIsNot(DataStatus dataStatus);

}
