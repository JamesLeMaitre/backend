package bj.gouv.sineb.authservice.application.code.service;

import bj.gouv.sineb.authservice.application.code.dto.payload.AuthEmailRequestEventPayload;
import bj.gouv.sineb.authservice.application.code.dto.payload.Mail;
import bj.gouv.sineb.authservice.application.code.dto.payload.MailData;
import org.springframework.messaging.MessagingException;
import org.springframework.validation.annotation.Validated;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.List;


@Validated
public interface EmailService {
    void sendSimpleMail(String to, String title, String content);
    void sendMailTemplateIncludeImage(String to, String title, MailData content) throws IOException;
    void sendMailTemplateForList(String to, String title, String templateName, List<MailData> customers) throws MessagingException, jakarta.mail.MessagingException;
    void sendMailWithAttachments(String to,
                                        String subject,
                                        String body,
                                        List<String> attachments) throws MessagingException, jakarta.mail.MessagingException;

    void sendMailTemplate(String to,
                                 String subject,
                                 Mail mail,
                                 Context context,
                                 String templateName) throws MessagingException, jakarta.mail.MessagingException;

    void sendAccountValidationEmail(AuthEmailRequestEventPayload event) throws Exception;
    void sendResetPasswordEmail(AuthEmailRequestEventPayload event) throws Exception;
    public void sendEmailResetEmail(AuthEmailRequestEventPayload event) throws Exception;


}
