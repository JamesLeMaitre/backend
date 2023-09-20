package bj.gouv.sineb.authservice.application.code.service.impl;

import bj.gouv.sineb.authservice.application.constant.AppAuthConstant;
import bj.gouv.sineb.authservice.application.interceptor.HttpRequestResponseUtils;
import bj.gouv.sineb.authservice.application.code.dto.simple.data.RessourceScopesDto;
import bj.gouv.sineb.authservice.application.code.entity.*;
import bj.gouv.sineb.authservice.application.code.dto.response.UserResponse;
import bj.gouv.sineb.authservice.application.code.repository.*;
import bj.gouv.sineb.authservice.application.code.dto.payload.AuthEmailRequestEventPayload;
import bj.gouv.sineb.authservice.application.code.dto.request.UserRequest;
import bj.gouv.sineb.authservice.application.code.mapper.RessourceMapper;
import bj.gouv.sineb.authservice.application.code.mapper.RoleMapper;
import bj.gouv.sineb.authservice.application.code.mapper.ScopeMapper;
import bj.gouv.sineb.authservice.application.code.mapper.UserMapper;
import bj.gouv.sineb.authservice.application.code.service.EmailService;
import bj.gouv.sineb.authservice.application.code.service.GroupeRessourceScopeService;
import bj.gouv.sineb.authservice.application.code.service.UserService;
import bj.gouv.sineb.common.advice.exception.CustomException;
import bj.gouv.sineb.common.advice.exception.CustomNotFoundException;
import bj.gouv.sineb.common.enums.AccountEventType;
import bj.gouv.sineb.common.enums.EmailUserStatus;
import bj.gouv.sineb.common.enums.UserStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final EmailService emailService;
    private final UserMapper userMapper;
    private final RessourceMapper ressourceMapper;
    private final RoleMapper roleMapper;
    private final VerificationTokenRepository verificationTokenRepository;
    private final GroupeUserRepository groupeUserRepository;
    private final GroupeRessourceScopeService groupeRessourceScopeService;
    private final UserRoleRepository userRoleRepository;
    private final AppEventRepository appEventRepository;
    private final PolicieRepository policieRepository;
    private final ScopeMapper scopeMapper;
    private final PasswordEncoder passwordEncoder;

    /*private final AuthMessagePublisher authMessagePublisher;
    private final UserApprovalEmailRequestOutboxHelper userApprovalEmailRequestOutboxHelper;
    private final AskNewValidationCodeEmailRequestOutboxHelper askNewValidationCodeEmailRequestOutboxHelper;
    private final RequestEmailResetEmailRequestOutboxHelper requestEmailResetEmailRequestOutboxHelper;
    private final RequestPasswordResetNoAuthEmailRequestOutboxHelper requestPasswordResetNoAuthEmailRequestOutboxHelper;*/

    public UserServiceImpl(UserRepository repository, EmailService emailService, UserMapper userMapper,
                           RessourceMapper ressourceMapper, VerificationTokenRepository verificationTokenRepository,
                           GroupeUserRepository groupeUserRepository,
                           GroupeRessourceScopeRepository groupeRessourceRepository,
                           RoleMapper roleMapper,
                           GroupeRessourceScopeService groupeRessourceScopeService,
                           UserRoleRepository userRoleRepository,
                           AppEventRepository appEventRepository,
                           PolicieRepository policieRepository,
                           ScopeMapper scopeMapper,
                           PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.emailService = emailService;
        this.userMapper = userMapper;
        this.ressourceMapper = ressourceMapper;
        this.verificationTokenRepository = verificationTokenRepository;
        this.groupeUserRepository = groupeUserRepository;
        this.roleMapper = roleMapper;
        this.groupeRessourceScopeService = groupeRessourceScopeService;
        this.userRoleRepository = userRoleRepository;
        this.appEventRepository = appEventRepository;
        this.policieRepository = policieRepository;
        this.scopeMapper = scopeMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public UserResponse save(UserRequest request) throws Exception {
        try {
            log.info("Saving User with payload: "+request.toString());
            User user =  userMapper.toEntity(request);
            user.setUserStatus(UserStatus.CREATED);
            log.info("User is created with id: {}", user.getId());
            UserResponse userResponse = userMapper.toDto(repository.save(user));
            log.info("Returning UserResponse with trackingId: {}", userResponse.getTrackingId());

            // sending email

            approveAccount(userResponse.getTrackingId());

            return userResponse;
        }catch (Exception exception){
            throw new Exception(exception.getMessage());
        }
    }

    @Override
    public void approveAccount(UUID userTrackingId) throws Exception {
        User user = repository.findByTrackingId(userTrackingId)
                .orElseThrow(()-> new CustomNotFoundException("User with id: "+userTrackingId+" not found!"));

        String tempPassword = generateAccountValidationCode();
        user.setUserStatus(UserStatus.APPROVED);
        user.setPassword(passwordEncoder.encode(tempPassword));
        repository.save(user);

        AppEvent appEvent = appEventRepository.findById(AppAuthConstant.APP_EVENT_SEND_ACCOUNT_CREDENTIALS_ID)
                .orElseThrow(()-> new CustomNotFoundException("Cannot get account credentials transfert app_event from database!"));

        log.info("Send email credentials to email with email: {} ...", user.getEmail());
        AuthEmailRequestEventPayload authEmailRequestEventPayload = AuthEmailRequestEventPayload.builder()
                .eventType(appEvent.getName())
                .temp(tempPassword)
                .toUserId(user.getId())
                .toUserEmail(user.getEmail())
                .toUserFirstname(user.getFirstname())
                .toUserLastName(user.getLastname())
                .createdAt(Instant.now())
                .emailUserStatus(EmailUserStatus.PENDING.name())
                .build();

        // Here we save email mail outbox payload for account validation email
        /*userApprovalEmailRequestOutboxHelper.saveAuthEmailOutboxMessage(
                authEmailRequestEventPayload,
                EmailStatus.PENDING,
                SagaStatus.STARTED,
                OutboxStatus.STARTED,
                UUID.randomUUID(), "UserCreationEmailOutboxMessage");*/

        log.info("Credentials for first login sent to email with email: {}", user.getEmail());
    }

    @Override
    public void disapproveAccount(UUID userTrackingId) throws Exception {
        User user = repository.findByTrackingId(userTrackingId)
                .orElseThrow(()-> new CustomNotFoundException("User with id: "+userTrackingId+" not found!"));
        user.setUserStatus(UserStatus.CREATED);
        repository.save(user);
        log.info("Account approval rolled back with email: {}", user.getEmail());
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public UserResponse register(UserRequest request) {
        log.info("Registration of User with payload: "+request.toString());
        User user =  userMapper.toEntity(request);
        log.info("User is created with id: {}", user.getId());
        UserResponse userResponse = userMapper.toDto(repository.save(user));
        log.info("Returning UserResponse with trackingId: {}", userResponse.getTrackingId());
        return userResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getOne(UUID id) {
        log.info("Searching for User with id: "+id);
        User user = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("User with id: "+id+" not found!"));
        log.info("User with id: {} is found", user.getId());
        UserResponse userResponse = userMapper.toDto(user);
        log.info("Returning UserResponse with trackingId: {}", userResponse.getTrackingId());
        return userResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public UUID getOneUserIdWithEmail(String email) {
        log.info("Searching for User with email: "+email);
        Optional<User> userOptional = repository.findByEmail(email);
        log.info("User optional found !");
        if (userOptional.isEmpty())
            throw new CustomNotFoundException("User with email: "+email+" not found!");

        log.info("User with email: {} is found", email);
        log.info("Returning UUID userId : {}", userOptional.get().getId());
        return userOptional.get().getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        log.info("Searching for User with email: "+email);
        Optional<User> userOptional = repository.findByEmail(email);
        log.info("Returning User Optional for the search above !");
        return userOptional;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(UUID id) {
        log.info("Searching for User with id: "+id);
        Optional<User> userOptional = repository.findById(id);
        log.info("Returning User for the search above !");
        return userOptional;
    }

    /*
    * NEVER USE THIS FUNCTION FOR OTHERS TASK.
    * IT DOEST NOT HANDLE uSER WITH EMAIL NOT FOUND EXCEPTION
    * JUST BECAUSE WE WANT TO USE THE SYSTEM EMAIL IF USER NOT FOUND.
    * THIS CASE IS BECAUSE JUST AFTER CALL FINISH EMAIL RESET THE OLD EMAIL USE
    * TO GET JWT TOKEN IS CHANGED TO THE NEW EMAIL AND WHEN THIS FMETHOD IS CALLED
    * TO SET JOURNAL EVENT CREATED_BY BASE ON THE OLD EMAIL, HE DOESN'T FIND
    * THE USER BUT USER STILL THERE. HE JUST CHANGE HIS EMAIL.
    * */
    @Override
    @Transactional(readOnly = true)
    public UserResponse getOneByEmailForTheOnlyUsageOfJournalEvent(String email) {
        log.info("Searching for User with email: "+email);
        Optional<User> userOptional = getUserByEmail(email);
        //.orElseThrow(() -> new CustomNotFoundException("User with email: "+email+" not found !"));
        UserResponse userResponse;
        if (userOptional.isEmpty()) {
            log.info("Using the system email ..");
            userResponse = getOne(AppAuthConstant.SYSTEM_USER_ID);
        }else {
            log.info("User with email: {} is found", email);
            userResponse = userMapper.toDto(userOptional.get());
        }
        log.info("Returning UserResponse for the search above !");
        return userResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getOneByTrackingId(UUID trackingId) {
        log.info("Searching for User with trackingId: "+trackingId);
        User user = repository.findByTrackingId(trackingId)
                .orElseThrow(() -> new CustomNotFoundException("User with trackingId: "+trackingId+" not found !"));
        log.info("User with trackingId: {} is found", trackingId);
        UserResponse userResponse = userMapper.toDto(user);
        log.info("Returning UserResponse for the search above !");
        return userResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAll(int page, int size, boolean enabled, boolean deleted) {
        log.info("Searching for User list with (page, size, enabled, deleted) : "+"("+page+", "+size+", "+enabled+", "+deleted+")");
        Page<User> userPage = repository
                .findAllByEnabledAndDeleted(enabled, deleted, PageRequest.of(page, size));
        log.info("User list found with size: {}", userPage.getTotalElements());
        Page<UserResponse> userPageResponse = userPage.map(userMapper::toDto);
        log.info("Returning UserResponse list with size: {}", userPage.getTotalElements());
        return userPageResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllConnected(int page, int size, boolean deleted) {
        log.info("Searching for User list with (page, size, deleted) : "+"("+page+", "+size+", "+deleted+")");
        Page<User> userPage = repository
                .findAllByDeletedAndEnabledTrueAndAccessTokenExpiredAtLessThan(deleted, Instant.now(), PageRequest.of(page, size));
        log.info("Users connected list found with size: {}", userPage.getTotalElements());
        Page<UserResponse> userPageResponse = userPage.map(userMapper::toDto);
        log.info("Returning UserResponse list with size: {}", userPage.getTotalElements());
        return userPageResponse;
    }

    @Override
    @Transactional
    public UserResponse updateOne(UserRequest request) {
        log.info("Updating User with payload: "+request.toString());

        if (request.getTrackingId()==null)
            throw new CustomException("User trackingId should not be null !");

        User user = repository.findByTrackingId(request.getTrackingId())
                .orElseThrow(()-> new CustomNotFoundException("User with id: "+request.getTrackingId()+" not found!"));
        user.update(request);
        UserResponse userResponse = userMapper.toDto(repository.save(user));
        log.info("User is updated with id: {}", user.getId());
        log.info("Returning UserResponse with trackingId: {}", userResponse.getTrackingId());
        return userResponse;
    }

    @Override
    @Transactional
    public void deleteOne(UUID trackingId) {
        log.info("Deleting User with trackingId:: "+trackingId);
        User user = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("AppEvent with trackingId: "+trackingId+" not found!"));
        user.delete();
        repository.save(user);
        log.info("User is deleted with id: {}", user.getId());
    }

    @Override
    @Transactional
    public void disableOne(UUID trackingId) {
        log.info("User deactivation with trackingId:: "+trackingId);
        User user = repository.findByTrackingId(trackingId)
                .orElseThrow(()-> new CustomNotFoundException("User with trackingId: "+trackingId+" not found!"));
        user.disable();
        repository.save(user);
        log.info("User is disabled with id: {}", user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        long count = repository.countByEnabledTrueAndDeletedFalse();
        log.info("User total count : {}", count);
        return count;
    }

    /* User account others actions */

    @Override
    @Transactional
    public void changePassword(String email, String currentPassword, String newPassword) throws Exception {
        log.info("Changing email password for email with email: {} ...", email);
        User user = getUserByEmail(email)
                .orElseThrow(() -> new CustomNotFoundException("User with email: "+email+" not found !"));
        log.info("User with email: {} found !", email);
        if (user.isAccountNonLocked()){
            if (passwordEncoder.matches(currentPassword, user.getPassword())){
                if (!user.isPasswordChangedOnce())
                    user.setPasswordChangedOnce(true);
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setCredentialsNonExpired(false);
                repository.save(user);
                log.info("User with email: {} password changed successfully !", email);
            }else
                throw new Exception("Current password is not correct !");
        }else
            throw new Exception("Unlock first of all your account !");

    }

    @Override
    @Transactional
    public void validateAccount(String email, String code) throws Exception {
        log.info("Validating account for email with email: {} ...", email);
        User user = getUserByEmail(email)
                .orElseThrow(() -> new CustomNotFoundException("User with email: "+email+" not found !"));

        List<VerificationToken> verificationTokenList = verificationTokenRepository
                .findAllByRevokedFalseAndUserIdAndAppEventId(user.getId(), AppAuthConstant.APP_EVENT_ACCOUNT_VALIDATION_ID);
        if (verificationTokenList.size()!=1)
            throw new Exception("Une erreur interne est survenue. Veiller contactez l'administrateur !");

        VerificationToken verificationToken = verificationTokenList.get(0);
        log.info("Current verefication token found for account validation of the email: {} si {}", email, verificationToken.getToken());
        if (verificationToken.getToken().equals(code)){
            log.info("token match with code ....");
            verificationToken.setRevoked(true);
            user.setAccountNonLocked(true);
            verificationTokenRepository.save(verificationToken);
            repository.save(user);
        }else {
            throw new CustomException("Account validation code: "+code+" is not correct !");
        }

    }

    @Override
    @Transactional
    public void askNewValidationCode(String email) throws Exception {
        log.info("Asking for new account validation code for email with email: {} ...", email);
        User user = getUserByEmail(email)
                .orElseThrow(() -> new CustomNotFoundException("User with email: "+email+" not found !"));

        log.info("User with email: {} found !", email);
        log.info("Revoking all old and valid account validation code. Only one ...");
        List<VerificationToken> verificationTokenList = verificationTokenRepository
                .findAllByRevokedFalseAndUserIdAndAppEventId(user.getId(), AppAuthConstant.APP_EVENT_ACCOUNT_VALIDATION_ID);
        verificationTokenList.forEach(verificationToken -> verificationToken.setRevoked(true));
        verificationTokenRepository.saveAll(verificationTokenList);
        log.info("Revoke successfully old account validation code for email with email: {}", email);

        AppEvent appEvent = appEventRepository.findById(AppAuthConstant.APP_EVENT_ACCOUNT_VALIDATION_ID)
                .orElseThrow(()-> new CustomNotFoundException("Cannot get account validation app_event from database!"));

        log.info("Generating new account validation code ...");
        String code = generateAccountValidationCode();
        log.info("New account validation code generated successfully  ...");

        VerificationToken verificationToken = VerificationToken.builder()
                .trackingId(UUID.randomUUID())
                .token(code)
                .revoked(false)
                .eventType(AccountEventType.REGISTRATION)
                .user(user)
                .appEvent(appEvent)
                .build();
        verificationTokenRepository.save(verificationToken);

        log.info("Send account validation code to email: {} ...", email);
        AuthEmailRequestEventPayload authEmailRequestEventPayload = AuthEmailRequestEventPayload.builder()
                .eventType(appEvent.getName())
                .token(code)
                .toUserId(user.getId())
                .toUserEmail(user.getEmail())
                .toUserFirstname(user.getFirstname())
                .toUserLastName(user.getLastname())
                .emailUserStatus(EmailUserStatus.PENDING.name())
                .createdAt(Instant.now())
                .build();

        // Here we save email mail outbox payload for account validation email
        /*askNewValidationCodeEmailRequestOutboxHelper.saveAuthEmailOutboxMessage(
                authEmailRequestEventPayload,
                EmailStatus.PENDING,
                SagaStatus.STARTED,
                OutboxStatus.STARTED,
                UUID.randomUUID(), "AskNewValidationCodeEmailOutboxMessage");*/

        log.info("New account validation code outbox payload saved successfully to: {}", email);
    }

    @Override
    @Transactional
    public void requestEmailReset(String newEmail) throws Exception {
        log.info("Requesting for email reset with newEmail: {} ...", newEmail);
        Optional<User> userOptional = getUserByEmail(newEmail);
        if (userOptional.isPresent())
            throw new CustomNotFoundException("User with email: "+newEmail+" already exist !");

        String userConnectedEmail = HttpRequestResponseUtils.getLoggedInUserName();
        User user = repository.findByEmail(userConnectedEmail)
                .orElseThrow(() -> new CustomNotFoundException("Unable to identify connected email !"));

        if (!user.isAccountNonLocked())
            throw new Exception("Unlock first of all your account !");

        log.info("Revoking all old and valid account validation code. Only one ...");
        List<VerificationToken> verificationTokenList = verificationTokenRepository
                .findAllByRevokedFalseAndUserIdAndAppEventId(user.getId(), AppAuthConstant.APP_EVENT_REQUEST_EMAIL_RESET_ID);
        verificationTokenList.forEach(verificationToken -> verificationToken.setRevoked(true));
        verificationTokenRepository.saveAll(verificationTokenList);
        log.info("Revoke successfully old account validation code for email with email: {}", user.getEmail());

        AppEvent appEvent = appEventRepository.findById(AppAuthConstant.APP_EVENT_REQUEST_EMAIL_RESET_ID)
                .orElseThrow(()-> new CustomNotFoundException("Cannot get email reset app_event from database!"));

        log.info("Generating new email reset code ...");
        String code = generateAccountValidationCode();
        log.info("New email reset code generated successfully  ...");

        VerificationToken verificationToken = VerificationToken.builder()
                .token(code)
                .revoked(false)
                .eventType(AccountEventType.EMAIL_RESET)
                .newEmailResetValue(newEmail)
                .user(user)
                .appEvent(appEvent)
                .createdAt(Instant.now())
                .build();

        verificationTokenRepository.save(verificationToken);

        log.info("Send account validation code to email: {} ...", newEmail);
        AuthEmailRequestEventPayload authEmailRequestEventPayload = AuthEmailRequestEventPayload.builder()
                .eventType(appEvent.getName())
                .token(code)
                .otherEmail(newEmail)
                .toUserId(user.getId())
                .toUserEmail(user.getEmail())
                .toUserFirstname(user.getFirstname())
                .toUserLastName(user.getLastname())
                .emailUserStatus(EmailUserStatus.PENDING.name())
                .createdAt(Instant.now())
                .build();

        // Here we save email mail outbox payload for requesting email reset
        /*requestEmailResetEmailRequestOutboxHelper.saveAuthEmailOutboxMessage(
                authEmailRequestEventPayload,
                EmailStatus.PENDING,
                SagaStatus.STARTED,
                OutboxStatus.STARTED,
                UUID.randomUUID(), "RequestEmailResetEmailOutboxMessage");*/

        log.info("Email reset code outbox payload saved successfully to: {}", newEmail);
    }

    @Override
    @Transactional
    public void finishEmailReset(String code) throws Exception {
        log.info("Finishing email event for email reset ...");
        String userConnectedEmail = HttpRequestResponseUtils.getLoggedInUserName();
        Optional<User> userOptional = repository.findByEmail(userConnectedEmail);

        if (userOptional.isEmpty())
            throw new CustomNotFoundException("Unable to identify connected email !");

        User user = userOptional.get();
        if (user.isAccountNonExpired()){
            List<VerificationToken> verificationTokenList = verificationTokenRepository.findAllByRevokedFalseAndUserIdAndAppEventIdAndToken(user.getId(), AppAuthConstant.APP_EVENT_REQUEST_EMAIL_RESET_ID, code);

            if (verificationTokenList.size()==1){
                log.info("Updating email email ...");
                user.setEmail(verificationTokenList.get(0).getNewEmailResetValue());
                user.setCredentialsNonExpired(false);
                repository.save(user);

                verificationTokenList.forEach(verificationToken -> verificationToken.setRevoked(true));
                verificationTokenRepository.saveAll(verificationTokenList);
            }else {
                throw new Exception("Only one verification token for an app-event rule is violated !");
            }
        }else {
            throw new Exception("Unlock first of all your account !");
        }
    }

    @Override
    @Transactional
    public void requestPasswordResetNoAuth(String email) throws Exception {
        log.info("Requesting password reset no email for new account validation code for email with email: {} ...", email);
        User user = getUserByEmail(email)
                .orElseThrow(() -> new CustomNotFoundException("User with email: "+email+" not found !"));

        if (user.isAccountNonLocked()){
            String tempPwd = generateAccountValidationCode();
            user.setTempPwd(passwordEncoder.encode(tempPwd));
            user.setTempPwdExpiredAt(Instant.now().plus(60, ChronoUnit.MINUTES));
            // email.setAccountNonLocked(false);
            // Bloquer le compte pour le forcer a changer de mot de passe autre que le temporaire recu par mail
            repository.save(user);

            AppEvent appEvent = appEventRepository.findById(AppAuthConstant.APP_EVENT_PASSWORD_REQUEST_NO_AUTH_ID)
                    .orElseThrow(()-> new CustomNotFoundException("Cannot get password reset no email validation app_event from database!"));

            log.info("Generating new account validation code ...");
            String code = generateAccountValidationCode();
            log.info("New account validation code generated successfully  ...");

            AuthEmailRequestEventPayload authEmailRequestEventPayload = AuthEmailRequestEventPayload.builder()
                    .eventType(appEvent.getName())
                    .temp(tempPwd)
                    .toUserId(user.getId())
                    .toUserEmail(user.getEmail())
                    .toUserFirstname(user.getFirstname())
                    .toUserLastName(user.getLastname())
                    .emailUserStatus(EmailUserStatus.PENDING.name())
                    .createdAt(Instant.now())
                    .build();

            // Here we save email mail outbox payload for reseting password no email
            /*requestPasswordResetNoAuthEmailRequestOutboxHelper.saveAuthEmailOutboxMessage(
                    authEmailRequestEventPayload,
                    EmailStatus.PENDING,
                    SagaStatus.STARTED,
                    OutboxStatus.STARTED,
                    UUID.randomUUID(), "RequestPasswordResetNoAuthEmailOutboxMessage");*/
            log.info("New temporary password outbox payload saved successfully to: {}", email);
        }
    }

    @Override
    @Transactional
    public void deleteTemporaryPasswordValue() {
        repository.findAll().stream()
                .filter(user -> user.getTempPwdExpiredAt()!=null)
                .filter(user -> user.getTempPwdExpiredAt().isBefore(Instant.now()) || user.getTempPwdExpiredAt().equals(Instant.now()))
                .forEach(user -> {
                    user.setTempPwd(null);
                    repository.save(user);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public boolean globalCredentialNonExpired() {
        User user = repository.findByEmail(HttpRequestResponseUtils.getLoggedInUserName())
                .orElseThrow(()-> new RuntimeException("Something were wrong in the system. Please contact devs admins !"));
        System.out.println("Crendentials expired : "+ user.isCredentialsNonExpired());
        return user.isCredentialsNonExpired();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getUserRolesListed(UUID userId) {
        return  userRoleRepository.findAllByActiveAndUserId(true, userId).stream()
                .map(UserRole::getRole)
                .map(Role::getName)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getUserScopesListed(UUID userTrackingId) {
        User user = repository.findByTrackingId(userTrackingId)
                .orElseThrow(()-> new CustomNotFoundException("User with id: "+userTrackingId+" not found!"));

        List<Policie> policieList = policieRepository.findAllByDeletedFalseAndExpiredFalseAndUserId(user.getId());

        if(policieList.isEmpty())
            return List.of();

        Map<UUID, List<Policie>> groupeRessourceScopeByRessourceId = policieList.stream()
                .collect(Collectors.groupingBy(g->g.getRessource().getId()));

        List<RessourceScopesDto> ressourceScopesDtoList = new ArrayList<>();

        for (Map.Entry<UUID, List<Policie>> entry : groupeRessourceScopeByRessourceId.entrySet()) {
            UUID ressourceId = entry.getKey();
            List<Policie> policieListListByRessourceId = entry.getValue();
            Ressource ressource = entry.getValue().get(0).getRessource();
            List<Scope> scopeList = policieListListByRessourceId.stream().map(Policie::getScope).toList();
            RessourceScopesDto ressourceScopesDto = RessourceScopesDto.builder()
                    .ressourceResponse(ressourceMapper.toDto(ressource))
                    .scopeResponseList(scopeList.stream().map(scopeMapper::toDto).toList())
                    .build();
            ressourceScopesDtoList.add(ressourceScopesDto);
        }

        List<String> fromPolicies = buildUserScopeListFromPoliciesNow(ressourceScopesDtoList);
        List<String> fromGroupeRessourceScope = groupeRessourceScopeService
                .buildUserScopeListNowFromGroupeRessourceScopes(groupeRessourceScopeService.
                        getAllGroupesRessourcesAndScopes(false));

        return  getCompleteUserScopesFromPoliciesAndGroupeRessourceScope(fromPolicies, fromGroupeRessourceScope);
    }


    @Override
    public List<String> getUserEmailChangeHistory(String email) {
        return repository.findRevisionsByEmail(email)
                .stream()
                .map(User::getEmail)
                .distinct()
                .toList();
    }


    private List<String> buildUserScopeListFromPoliciesNow(List<RessourceScopesDto> ressourceScopesDtoList) {
        List<String> response = new ArrayList<>();
        ressourceScopesDtoList.forEach(ressourceScopesDto -> {
            ressourceScopesDto.getScopeResponseList().forEach(scopeDto -> {
                String joinedScope = ressourceScopesDto.getRessourceResponse().getName().toUpperCase()+"_"+scopeDto.getName().toUpperCase();
                response.add(joinedScope);
            });
        });

        //And now we remove all duplicate scope
        Set<String> uniqueSet = new HashSet<>();
        List<String> result = new ArrayList<>();

        for (String element : response) {
            if (uniqueSet.add(element)) {
                result.add(element);
            }
        }

        System.out.println("My Scope LIst from policies : "+result.toString());

        return result;
    }

    private List<String> getCompleteUserScopesFromPoliciesAndGroupeRessourceScope(List<String> fromPolicies,
                                                                                  List<String> fromGroupeRessourceScope) {
        List<String> userScopesWithPossibleDuplications = new ArrayList<>();
        userScopesWithPossibleDuplications.addAll(fromPolicies);
        userScopesWithPossibleDuplications.addAll(fromGroupeRessourceScope);

        //And now we remove all duplicate scope
        Set<String> uniqueSet = new HashSet<>();
        List<String> result = new ArrayList<>();

        for (String element : userScopesWithPossibleDuplications) {
            if (uniqueSet.add(element)) {
                result.add(element);
            }
        }

        System.out.println("My complete scopes list : "+result.toString());

        return result;
    }


    private String generateAccountValidationCode(){
        return RandomStringUtils.randomAlphabetic(6).toUpperCase();
    }
}
