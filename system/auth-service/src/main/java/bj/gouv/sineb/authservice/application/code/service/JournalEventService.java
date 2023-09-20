package bj.gouv.sineb.authservice.application.code.service;

import bj.gouv.sineb.authservice.application.code.dto.request.JournalEventRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.JournalEventResponse;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface JournalEventService {
    void save(JournalEventRequest request);
    JournalEventResponse getOne(UUID id);

    JournalEventResponse getOneByTrackingId(UUID trackingId);
    Page<JournalEventResponse> getAll(int page, int size);
    long count();
}
