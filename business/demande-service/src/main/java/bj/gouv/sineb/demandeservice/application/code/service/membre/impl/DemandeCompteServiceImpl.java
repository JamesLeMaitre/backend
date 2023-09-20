package bj.gouv.sineb.demandeservice.application.code.service.membre.impl;

import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.demandeservice.application.code.entity.common.EmailVerify;
import bj.gouv.sineb.demandeservice.application.code.entity.common.Pays;
import bj.gouv.sineb.demandeservice.application.code.entity.membre.DemandeCompte;
import bj.gouv.sineb.demandeservice.application.code.entity.membre.TypeCompte;
import bj.gouv.sineb.demandeservice.application.code.mapper.membre.DemandeCompteMapper;
import bj.gouv.sineb.demandeservice.application.code.repository.common.*;
import bj.gouv.sineb.demandeservice.application.code.repository.membre.DemandeCompteRepository;
import bj.gouv.sineb.demandeservice.application.code.repository.membre.TypeCompteRepository;
import bj.gouv.sineb.demandeservice.application.code.service.membre.DemandeCompteService;
import bj.gouv.sineb.demandeservice.common.dto.request.membre.DemandeCompteRequest;
import bj.gouv.sineb.demandeservice.common.dto.response.membre.DemandeCompteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class DemandeCompteServiceImpl implements DemandeCompteService {

    private final DemandeCompteRepository repository;

    private final EmailVerifyRepository emailVerifyRepository;

    private final DemandeCompteMapper mapper;

    private static final int OTP_LENGTH = 4; // Longueur du code OTP


    /**
     * @param request
     * @return
     */
    @Override
    public DemandeCompteResponse save(DemandeCompteRequest request) {
        return Optional.of(request).stream()
                .map(mapper::toEntity)
                .peek(repository::save)
                .peek(this::newEmailVerify)
                .map(mapper::toDto)
                .findFirst()
                .orElseThrow();
    }

    /**
     * @param request
     * @param trackingId
     * @return
     */
    @Override
    public DemandeCompteResponse update(DemandeCompteRequest request, UUID trackingId) {
        if (trackingId == null)
            throw new CustomException("Country trackingId should not be null !");
        DemandeCompte compte = repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .orElseThrow();
        compte.setDataStatus(DataStatus.UPDATED);
        return Optional.of(request)
                .map(DemandeCompteRequest -> mapper.toEntity(request, compte))
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow();
    }

    /**
     * @param trackingId
     * @return
     */
    @Override
    public DemandeCompteResponse delete(UUID trackingId) {
        return repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .stream()
                .peek(compte -> compte.setDataStatus(DataStatus.DELETED))
                .map(repository::save)
                .map(mapper::toDto)
                .findFirst()
                .orElseThrow(() -> new CustomNotFoundException("DemandeCompte not found for id "+trackingId));
    }

    /**
     * @param trackingId
     * @return
     */
    @Override
    public DemandeCompteResponse getOne(UUID trackingId) {
        return repository.findByDataStatusIsNotAndTrackingId(DataStatus.DELETED, trackingId)
                .map(mapper::toDto)
                .orElseThrow();
    }

    /**
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<DemandeCompteResponse> getAll(int page, int size) {
        return repository.findDemandeCompteByDataStatusIsNot(DataStatus.DELETED, PageRequest.of(page, size))
                .map(mapper::toDto);
    }

    /**
     * @param typeDemandeId
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<DemandeCompteResponse> getAllByTypeDemande(UUID typeDemandeId, int page, int size) {
        return repository.findDemandeCompteByDataStatusIsNotAndTypeDemande(DataStatus.DELETED, typeDemandeId, PageRequest.of(page, size))
                .map(mapper::toDto);
    }

    /**
     * @param typeCompteId
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<DemandeCompteResponse> getAllByTypeCompte(UUID typeCompteId, int page, int size) {
        return repository.findDemandeCompteByDataStatusIsNotAndTypeCompte(DataStatus.DELETED, typeCompteId, PageRequest.of(page, size))
                .map(mapper::toDto);
    }

    /**
     * @param civiliteId 
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<DemandeCompteResponse> getAllByCivilite(UUID civiliteId, int page, int size) {
        return repository.findDemandeCompteByDataStatusIsNotAndCivilite(DataStatus.DELETED, civiliteId, PageRequest.of(page, size))
                .map(mapper::toDto);
    }

    /**
     * @param paysId 
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<DemandeCompteResponse> getAllByPays(UUID paysId, int page, int size) {
        return repository.findDemandeCompteByDataStatusIsNotAndPays(DataStatus.DELETED, paysId, PageRequest.of(page, size))
                .map(mapper::toDto);
    }

    /**
     * @param email 
     * @return
     */
    @Override
    public boolean emailValid(String email) {
        // regex pour valider une adresse e-mail
        String regex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    /**
     * @param email 
     * @return
     */
    @Override
    public boolean emailPresent(String email) {
        return repository.findByDataStatusIsNotAndMail(DataStatus.DELETED, email).isPresent();
    }

    private EmailVerify newEmailVerify(DemandeCompte compte) {
        int code = Integer.parseInt(genererOTP());
        Date expiration = calculerDateExpiration();
        EmailVerify verify = new EmailVerify();
        verify.setCode(code);
        verify.setExpiration(expiration);
        verify.setEmail(compte.getMail());
        verify.setStatus(false);
        verify.setCodeAuto(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10).toUpperCase());
        return emailVerifyRepository.save(verify);
    }


    public int generateEmailVerifyCode(){
        Random random = new Random();
        int code = random.nextInt(9000)+ 1000;
        System.out.println("*********************************");
        System.out.println("Code : "+code);
        System.out.println("*********************************");
        return code;
    }

    public String genererOTP() {
        // Générer un code OTP aléatoire de la longueur spécifiée
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10)); // Les codes OTP sont généralement composés de chiffres
        }
        return otp.toString();
    }

    public Date calculerDateExpiration() {
        // Calculer la date d'expiration en ajoutant 5 minutes à l'instant présent
        Date now = new Date();
        long expirationMillis = now.getTime() + TimeUnit.MINUTES.toMillis(5); // 5 minutes en millisecondes
        return new Date(expirationMillis);
    }
}
