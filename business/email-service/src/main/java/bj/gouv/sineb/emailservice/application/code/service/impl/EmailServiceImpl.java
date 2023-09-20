package bj.gouv.sineb.emailservice.application.code.service.impl;

import bj.gouv.sineb.emailservice.application.code.dto.request.EmailRequest;
import bj.gouv.sineb.emailservice.application.code.dto.response.EmailResponse;
import bj.gouv.sineb.emailservice.application.code.mapper.EmailMapper;
import bj.gouv.sineb.emailservice.application.code.repository.EmailRepository;
import bj.gouv.sineb.emailservice.application.code.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import org.thymeleaf.context.Context;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {

    private final EmailRepository repository;

    private final EmailMapper mapper;

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailServiceImpl(EmailRepository repository, EmailMapper mapper, JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.repository = repository;
        this.mapper = mapper;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @Transactional
    public EmailResponse send(EmailRequest request) {
        return Optional.of(request).stream()
                .peek(this::sendMimeMessage)
                .map(mapper::toEntity)
                .peek(repository::save)
                .map(mapper::toDto)
                .findFirst()
                .orElseThrow();
    }


    @SneakyThrows
    public void sendMimeMessage(EmailRequest request) {
        Context context = new Context();
        MimeMessage message = mailSender.createMimeMessage();
        Map<String, Object> model = new HashMap<>();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        String html = templateEngine.process("EMAIL_TEMPLATE_01", context);

        helper.setText(html, true);
        helper.setFrom(request.getMailFrom());
        helper.setTo(request.getMailTo());
//        helper.setCc(request.getMailCc());
//        helper.setBcc(request.getMailBcc());
        helper.setSubject(request.getMailSubject());
        helper.setText(request.getMailContent());
        mailSender.send(message);
    }

}
