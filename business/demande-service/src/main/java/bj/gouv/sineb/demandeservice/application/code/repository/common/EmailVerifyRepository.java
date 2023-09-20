package bj.gouv.sineb.demandeservice.application.code.repository.common;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.demandeservice.application.code.entity.common.EmailVerify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailVerifyRepository extends JpaRepository<EmailVerify, UUID> {
    Optional<EmailVerify> findByDataStatusIsNotAndStatusIsNotAndCode(DataStatus dataStatus, boolean status, int code);

}
