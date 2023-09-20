package bj.gouv.sineb.authservice.application.code.service.impl;

import bj.gouv.sineb.authservice.application.code.dto.payload.AuthEmailRequestEventPayload;
import bj.gouv.sineb.authservice.application.code.dto.payload.Mail;
import bj.gouv.sineb.authservice.application.code.dto.payload.MailData;
import bj.gouv.sineb.authservice.application.code.service.EmailService;
import bj.gouv.sineb.common.enums.MailSubjectType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

@Slf4j
@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    //https://github.com/tirmizee/SpringBoot-Email-Thymeleaf-Template

    private final JavaMailSender mailSender;
    private final ApplicationContext applicationContext;
    private final SpringTemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender mailSender,
                            ApplicationContext applicationContext,
                            SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.applicationContext = applicationContext;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendAccountValidationEmail(AuthEmailRequestEventPayload event) throws Exception {

        log.info("Sending account validation email {}",event.toString());

        try{
            Mail mail = Mail.builder()
                    .mailTo(event.getToUserEmail())
                    .build();

            //String link = getServerBaseUrl()+"/api/code/email/na/activation?token="+event.getToken()+"&event="+event.getEventType().toLowerCase();

            String code = event.getToken();
            Context context = new Context();
            context.setVariable("nom", event.getToUserFirstname()+" "+event.getToUserLastName());
            context.setVariable("code", code);

            sendMailTemplate(mail.getMailTo(),
                    MailSubjectType.values.get(0).getLibelle(),
                    mail, context, "EMAIL_TEMPLATE_ACCOUNT_VALIDATION"
            );
        }catch (Exception exception){
           throw new Exception("Failed to send account validation code to email: {}"+event.getToUserEmail());
        }
    }

    @Override
    public void sendResetPasswordEmail(AuthEmailRequestEventPayload event) throws Exception {

        log.info("Sending reset password email: {}", event.toString());

        Mail mail = Mail.builder()
                .mailTo(event.getToUserEmail())
                .build();
        Context context = new Context();
        context.setVariable("nom", event.getToUserFirstname()+" "+event.getToUserLastName());
        context.setVariable("temp", event.getTemp());
        log.info("Temporary password is : {}", event.getTemp());
        sendMailTemplate(mail.getMailTo(),
                MailSubjectType.values.get(1).getLibelle(),
                mail, context, "EMAIL_TEMPLATE_RESET_PASSWORD"
        );
    }

    @Override
    public void sendEmailResetEmail(AuthEmailRequestEventPayload event) throws Exception {

        log.info("Sending reset email email: {} ... ", event.toString());

        // CAN WRITE THIS BETTER : DELAY ...
        Mail mail = Mail.builder()
                .mailTo(event.getOtherEmail())
                .build();
        /*String link = getServerBaseUrl()+"/api/code/email/na/finish-reset-email?token="+event.getToken()+"&event="+event.getEventType().toLowerCase()
                +"&newEmail="+event.getOtherEmail();*/
        Context context = new Context();
        context.setVariable("nom", event.getToUserFirstname()+" "+event.getToUserLastName());
        context.setVariable("temp", event.getToken());

        sendMailTemplate(mail.getMailTo(),
                MailSubjectType.values.get(2).getLibelle(),
                mail, context, "EMAIL_TEMPLATE_RESET_EMAIL"
        );
    }


    @Override
    public void sendSimpleMail(String to, String title, String content) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("spring.email.from@gmail.com");
            message.setTo(to);
            message.setText(content);
            message.setSubject(title);

            mailSender.send(message);
            System.out.println("Mail Sent...");
        }catch(Exception ex){
            throw new RuntimeException("Error while sending mail. error="+ex.getMessage());
        }
    }

    @Override
    public void sendMailTemplateIncludeImage(String to, String title, MailData content) throws IOException {

    }

    @Override
    public void sendMailTemplateForList(String to, String subject, String templateName, List<MailData> datas) throws MessagingException, MessagingException, MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        //Adding list variable
        Context context = new Context();
		/*context.setVariable("cusNo", datas.getCusName());
		context.setVariable("cusName", datas.getCusName());
		context.setVariable("cusTel", datas.getCusName());
		context.setVariable("cusAddress", datas.getCusName());*/

        //Add context data to the template engine
        String html = templateEngine.process("EMAIL-TEMPLATE_LIST", context);

        mimeMessageHelper.setFrom("spring.email.from@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setSubject(subject);

        mailSender.send(mimeMessage);
        System.out.println("Mail Sent...");

    }


    @Override
    public void sendMailWithAttachments(String to,
                                        String subject,
                                        String body,
                                        List<String> attachments) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("spring.email.from@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        if (attachments!=null){
            attachments.forEach(attachment->{
                FileSystemResource fileSystem
                        = new FileSystemResource(new File(attachment));

                try {
                    mimeMessageHelper.addAttachment(fileSystem.getFilename(),
                            fileSystem);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });
        }


        mailSender.send(mimeMessage);
        System.out.println("Mail Sent...");

    }



    // Apres le mail notifier dans la base de donnee mailsent dans la table verification token
    @Override
    public void sendMailTemplate(String to,
                                 String subject,
                                 Mail mail,
                                 Context context,
                                 String templateName) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);


        //Add context data to the template engine
        String html = templateEngine.process(templateName, context);

        mimeMessageHelper.setFrom("spring.email.from@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setSubject(subject);

        mailSender.send(mimeMessage);
        System.out.println("Mail Sent...");

    }

    public String getServerAddress() throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();
        return inetAddress.getHostAddress();
    }
    public int getServerPort() throws Exception {
        Environment env = applicationContext.getEnvironment();
        String portString = env.getProperty("email.port");
        int port = Integer.parseInt(portString);
        return port;
    }
    public String getServerBaseUrl() throws Exception {
        String serverAddress = getServerAddress();
        int serverPort = getServerPort();
        return "http://" + serverAddress + ":" + serverPort;
    }

}
