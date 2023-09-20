package bj.gouv.sineb.authservice.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class AuditEvent<T> extends ApplicationEvent {

    public AuditEvent(T event) {
        super(event);
    }
}
