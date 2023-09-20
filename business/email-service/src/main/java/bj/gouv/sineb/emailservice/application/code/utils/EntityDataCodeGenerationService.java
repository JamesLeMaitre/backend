package bj.gouv.sineb.emailservice.application.code.utils;

import bj.gouv.sineb.emailservice.application.code.repository.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EntityDataCodeGenerationService {

    private final EmailRepository emailRepository;

    public EntityDataCodeGenerationService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public String generateEntityDataCode(String tableName) {
        switch (tableName) {
            case "Email":
                return "EML-"+emailRepository.count();
            default:
                log.info("EntityDataCodeGenerationRepository default behavior called ...");
                return "";
        }
    }


}
