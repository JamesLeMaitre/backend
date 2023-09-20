package bj.gouv.sineb.emailservice.old.repositories;



import bj.gouv.sineb.emailservice.old.entities.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail, Long> {
}
