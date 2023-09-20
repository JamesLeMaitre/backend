package bj.gouv.sineb.ddbservice.application.code.repository.common;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.ddbservice.application.code.entity.common.SousCategoryElt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SousCategoryEltRepository extends RevisionRepository<SousCategoryElt, UUID, Long>, JpaRepository<SousCategoryElt, UUID> {

    Page<SousCategoryElt> findAllByDataStatusIsNot(DataStatus dataStatus, Pageable pageable);
    Page<SousCategoryElt> findAllByDataStatusIsNotAndCategoryEltTrackingId(DataStatus  dataStatus, UUID categoryEltTrackingId, Pageable pageable);
    Optional<SousCategoryElt> findByTrackingId(UUID trackingId);
    long countAllByDataStatusIsNot(DataStatus dataStatus);

}
