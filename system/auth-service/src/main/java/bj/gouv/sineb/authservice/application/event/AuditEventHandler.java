package bj.gouv.sineb.authservice.application.event;


import bj.gouv.sineb.authservice.application.code.dto.request.JournalEventRequest;
import bj.gouv.sineb.authservice.application.code.repository.UserRepository;
import bj.gouv.sineb.authservice.application.code.service.JournalEventService;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AuditEventHandler {

    private final JournalEventService journalEventService;
    private final UserRepository userRepository;

    public AuditEventHandler(JournalEventService journalEventService, UserRepository userRepository) {
        this.journalEventService = journalEventService;
        this.userRepository = userRepository;
    }


    @SneakyThrows
    @EventListener
    @Async
    public void handleEvent(AuditEvent<JournalEventRequest> auditEvent){
        // Save the action
        journalEventService.save((JournalEventRequest) auditEvent.getSource());

        /*System.out.println(new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(auditEvent.getSource()));*/
    }
}
