package bj.gouv.sineb.authservice.application.code.repository;

import bj.gouv.sineb.authservice.application.code.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {

    Optional<VerificationToken> findByTrackingId(UUID trackingId);
    List<VerificationToken> findAllByRevokedFalseAndUserIdAndAppEventId(UUID userId, UUID appEventId);

    List<VerificationToken> findAllByRevokedFalseAndUserIdAndAppEventIdAndToken(UUID userId, UUID appEventId, String code);

}
