package bj.gouv.sineb.authservice.application.code.service.impl;

import bj.gouv.sineb.authservice.application.code.entity.JournalEvent;
import bj.gouv.sineb.authservice.application.code.mapper.JournalEventMapper;
import bj.gouv.sineb.authservice.application.code.dto.request.JournalEventRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.JournalEventResponse;
import bj.gouv.sineb.authservice.application.code.repository.JournalEventRepository;
import bj.gouv.sineb.authservice.application.code.service.JournalEventService;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class JournalEventServiceImpl implements JournalEventService {

    private final JournalEventRepository repository;
    private final JournalEventMapper journalEventMapper;

    public JournalEventServiceImpl(JournalEventRepository repository, JournalEventMapper journalEventMapper) {
        this.repository = repository;
        this.journalEventMapper = journalEventMapper;
    }


    @Override
    @Transactional
    public void save(JournalEventRequest request) {
        log.info("Saving JournalEvent with payload: "+request.toString());
        JournalEvent journalEvent = journalEventMapper.toEntity(request);
        JournalEventResponse journalEventResponse = journalEventMapper.toDto(repository.save(journalEvent));
        log.info("JournalEvent is created with id: {}", journalEvent.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public JournalEventResponse getOne(UUID id) {
        log.info("Searching for JournalEvent with id: "+id);
        JournalEvent journalEvent = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("JournalEvent with id: "+id+" not found!"));
        JournalEventResponse journalEventResponse = journalEventMapper.toDto(journalEvent);
        log.info("JournalEvent is found with id: {}", journalEvent.getId());
        log.info("Returning JournalEventResponse with trackingId: {}", journalEventResponse.getTrackingId());
        return journalEventResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public JournalEventResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for JournalEvent with trackingId: "+trackingId);
        JournalEvent journalEvent = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("JournalEvent with trackingId: "+trackingId+" not found!"));
        JournalEventResponse journalEventResponse = journalEventMapper.toDto(journalEvent);
        log.info("JournalEvent is found with trackingId: {}", journalEvent.getTrackingId());
        log.info("Returning JournalEventResponse with trackingId: {}", journalEventResponse.getTrackingId());
        return journalEventResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JournalEventResponse> getAll(int page, int size) {
        log.info("Searching for JournalEvent list with (page, size, deleted) : "+"("+page+", "+size+")");
        Page<JournalEvent> journalEventPage = repository.findAll(PageRequest.of(page, size));
        Page<JournalEventResponse> journalEventResponsePage = journalEventPage.map(journalEventMapper::toDto);
        log.info("JournalEvent list found with size: {}", journalEventResponsePage.getTotalElements());
        log.info("Returning JournalEventResponse list with size: {}", journalEventPage.getTotalElements());
        return journalEventResponsePage;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.count();
        log.info("JournalEvent total count : {}", count);
        return count;
    }
}
