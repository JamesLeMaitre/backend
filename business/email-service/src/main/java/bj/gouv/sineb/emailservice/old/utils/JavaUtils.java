package bj.gouv.sineb.emailservice.old.utils;


import bj.gouv.sineb.emailservice.old.dto.MailRequest;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class JavaUtils {
    private final JavaMailSender mailSender;
    private final Configuration config;

    @SneakyThrows
    public void sendMimeMessage(MailRequest request) {
        MimeMessage message = mailSender.createMimeMessage();
        Map<String, Object> model = new HashMap<>();
        model.put("name", request.getName());
        model.put("text", request.getText());
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Template t = config.getTemplate("email-pro.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        // Ajout de l'image en pièce jointe et en ligne (pour l'afficher dans le modèle)
//        helper.addAttachment("logo.jpg", new ClassPathResource("logo.jpg"));
      //  helper.addInline("sinebLogo", new ClassPathResource("logo.jpg"));
       // helper.setPriority(1);
        helper.setTo(request.getMailTo());
        helper.setText(html, true);
        helper.setSubject(request.getSubject());
        helper.setFrom(request.getMailFrom());
        mailSender.send(message);
    }


}
