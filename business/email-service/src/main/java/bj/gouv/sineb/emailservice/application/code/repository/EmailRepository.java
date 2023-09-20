package bj.gouv.sineb.emailservice.application.code.repository;

import bj.gouv.sineb.emailservice.application.code.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailRepository  extends JpaRepository<Email, UUID> {
}
