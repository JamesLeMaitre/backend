package bj.gouv.sineb.emailservice.old.services;
import bj.gouv.sineb.emailservice.old.dto.MailRequest;
import bj.gouv.sineb.emailservice.old.responses.MailResponse;

public interface MailService {

     MailResponse sendMail(MailRequest m);


//     List<Mail> getAllMaill();

}
