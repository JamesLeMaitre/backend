package bj.gouv.sineb.emailservice.old.services.impls;


import bj.gouv.sineb.emailservice.old.exceptions.MailException;
import bj.gouv.sineb.emailservice.old.utils.JavaConverter;
import bj.gouv.sineb.emailservice.old.utils.JavaUtils;
import bj.gouv.sineb.emailservice.old.repositories.MailRepository;
import bj.gouv.sineb.emailservice.old.dto.MailRequest;
import bj.gouv.sineb.emailservice.old.responses.MailResponse;
import bj.gouv.sineb.emailservice.old.services.MailService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class MailImpl implements MailService {
    private final MailRepository mailRepository;
    private final JavaConverter convert;
    private final JavaUtils utils;
    private final JavaMailSender mailSender;

    @Override
    @SneakyThrows
    public MailResponse sendMail(MailRequest request) {
        return Optional.of(request).stream()
                .peek(utils::sendMimeMessage)
                .map(convert::mailRequestToObject)
                .peek(mailRepository::save)
                .map(convert::objectToResponse)
                .findFirst()
                .orElseThrow(() -> new MailException("Error while saving due to send mail"));
    }


    @Bean
    public Consumer<MailRequest> eventConsumer() {
        return (input) -> {
            System.out.println("************Input****************");
            System.out.println(input.toString());
            System.out.println("****************************");
//            MailRequest mailRequest = MailRequest.builder().build();
//            BeanUtils.copyProperties(input, mailRequest);
            sendMail(input);
            System.out.println("****************************");
            System.out.println("Mail Sent");
            System.out.println("****************************");
        };
    }

//    @Override
//    public List<Mail> getAllMaill() {
//        return mailRepository.findAll();
//    }


}
