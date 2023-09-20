package bj.gouv.sineb.authservice.application.code.service.impl;

import bj.gouv.sineb.authservice.application.code.entity.Policie;
import bj.gouv.sineb.authservice.application.code.entity.User;
import bj.gouv.sineb.authservice.application.code.helper.PolicieHelper;
import bj.gouv.sineb.authservice.application.code.dto.request.PolicieRequest;
import bj.gouv.sineb.authservice.application.code.dto.request.PolicieUpdateRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.PolicieResponse;
import bj.gouv.sineb.authservice.application.code.mapper.PolicieMapper;
import bj.gouv.sineb.authservice.application.code.repository.PolicieRepository;
import bj.gouv.sineb.authservice.application.code.repository.RessourceRepository;
import bj.gouv.sineb.authservice.application.code.repository.ScopeRepository;
import bj.gouv.sineb.authservice.application.code.repository.UserRepository;
import bj.gouv.sineb.authservice.application.code.service.PolicieService;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class PolicieServiceImpl implements PolicieService {
    private final PolicieRepository repository;
    private final PolicieMapper policieMapper;
    private final PolicieHelper policieHelper;
    private final RessourceRepository ressourceRepository;
    private final ScopeRepository scopeRepository;
    private final UserRepository userRepository;

    public PolicieServiceImpl(PolicieRepository repository, PolicieMapper policieMapper, PolicieHelper policieHelper, RessourceRepository ressourceRepository, ScopeRepository scopeRepository, UserRepository userRepository) {
        this.repository = repository;
        this.policieMapper = policieMapper;
        this.policieHelper = policieHelper;
        this.ressourceRepository = ressourceRepository;
        this.scopeRepository = scopeRepository;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public void save(PolicieRequest request) {
        log.info("Saving Policie with payload: "+request.toString());
        List<Policie> policieList = policieMapper.toEntity(request);
        repository.saveAll(policieList);
        log.info("Policie is created with trackingId: {}", policieList.get(0).getTrackingId());
        log.info("Returning PolicieResponse with trackingId: {}", policieList.get(0).getTrackingId());
    }

    @Override
    @Transactional(readOnly = true)
    public PolicieResponse getOne(UUID id) {
        log.info("Searching for Policie with id: "+id);
        Policie policie = repository.findById(id)
                .orElseThrow(()-> new CustomNotFoundException("Policie with id: "+id+" not found!"));
        PolicieResponse policieResponse = policieMapper.toDto(policie);
        log.info("Policie is found with id: {}", policie.getId());
        log.info("Returning PolicieResponse with trackingId: {}", policieResponse.getTrackingId());
        return policieResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public PolicieResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for Policie with trackingId: "+trackingId);
        Policie policie = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("Policie with trackingId: "+trackingId+" not found!"));
        PolicieResponse policieResponse = policieMapper.toDto(policie);
        log.info("Policie is found with trackingId: {}", policie.getTrackingId());
        log.info("Returning PolicieResponse with trackingId: {}", policieResponse.getTrackingId());
        return policieResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PolicieResponse> getAll(int page, int size, boolean deleted, boolean expired) {
        log.info("Searching for Policie list with (page, size, deleted) : "+"("+page+", "+size+", "+deleted+")");
        Page<Policie> policiePage = repository.findAllByDeletedAndExpired(deleted, expired, PageRequest.of(page, size));
        Page<PolicieResponse> policiePageResponse = policiePage.map(policieMapper::toDto);
        log.info("Policie list found with size: {}", policiePage.getTotalElements());
        log.info("Returning PolicieResponse list with size: {}", policiePage.getTotalElements());
        return policiePageResponse;
    }


    @Override
    @Transactional
    public PolicieResponse updateOne(PolicieUpdateRequest request) {
        log.info("Updating Policie with payload: "+request.toString());
        Policie policie = repository.findByDeletedFalseAndUserTrackingIdAndRessourceTrackingIdAndScopeTrackingId(request.getUserTrackingId(), request.getRessourceTrackingId(), request.getScopeTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("Policie with trackingId: "+request.getTrackingId()+" not found!"));
        policie.update(request);
        PolicieResponse policieResponse = policieMapper.toDto(repository.save(policie));
        log.info("Policie is updated with id: {}", policie.getId());
        log.info("Returning PolicieResponse with trackingId: {}", policieResponse.getTrackingId());
        return policieResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID userTrackingId, UUID ressourceTrackingId, UUID scopeTrackingId) {

        log.info("Deleting one policie with payload: (userTrackingId, ressourceTrackingId, scopeTrackingId) : " +
                ""+"("+userTrackingId+", "+ressourceTrackingId+", "+scopeTrackingId+")");
        Policie policie = policieHelper.delete(userTrackingId, ressourceTrackingId, scopeTrackingId);
        log.info("Policie is deleted with id: {}", policie.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countByDeletedFalseAndExpiredFalse();
        log.info("Policie total count : {}", count);
        return count;
    }

    @Override
    @Transactional
    public void revokeExpiredPolicies() {
        log.info("Trying to revoke expired policies");
        List<Policie> policieList = repository.findAllByDeletedAndExpired(false, false);
        if (policieList.isEmpty())
            log.info("No expired policie at this time : "+ Instant.now());
        else {
            repository.findAllByDeletedAndExpired(false, false).stream()
                    .filter(Objects::nonNull)
                    .filter(policie -> policie.getEndDate().isBefore(Instant.now()) || policie.getEndDate().equals(Instant.now()))
                    .forEach(policie -> {
                        policie.setExpired(false);
                        Policie p = repository.save(policie);
                        User user = p.getUser();
                        user.setCredentialsNonExpired(false);
                        userRepository.save(user); // Ils doivent se reconnecter pour avoir un autre token base' sur leurs policies non expired
                    });
            log.info("Expired policies revoked successfully!");
        }
    }
}
