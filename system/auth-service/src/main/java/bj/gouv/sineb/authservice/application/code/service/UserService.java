package bj.gouv.sineb.authservice.application.code.service;

import bj.gouv.sineb.authservice.application.code.dto.request.UserRequest;
import bj.gouv.sineb.authservice.application.code.dto.response.UserResponse;
import bj.gouv.sineb.authservice.application.code.entity.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
public interface UserService {
    UserResponse save(@Valid UserRequest request) throws Exception;
    void approveAccount(UUID userTrackingId) throws Exception;
    void disapproveAccount(UUID userTrackingId) throws Exception;
    User save(User user);
    UserResponse register(@Valid UserRequest request);
    UserResponse getOne(UUID id);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(UUID id);
    UUID getOneUserIdWithEmail(String email);
    UserResponse getOneByEmailForTheOnlyUsageOfJournalEvent(String email);
    UserResponse getOneByTrackingId(UUID trackingId);
    Page<UserResponse> getAll(int page, int size, boolean enabled, boolean deleted);
    Page<UserResponse> getAllConnected(int page, int size, boolean deleted);
    List<String> getUserEmailChangeHistory(String email);
    UserResponse updateOne(@Valid UserRequest request);
    void deleteOne(UUID trackingId);
    long count();
    void changePassword(String email, String currentPassword, String newPassword) throws Exception;
    void validateAccount(String email, String code) throws Exception;
    void askNewValidationCode(String email) throws Exception;
    void requestEmailReset(String newEmail) throws Exception;
    void finishEmailReset(String code) throws Exception;
    void requestPasswordResetNoAuth(String email) throws Exception;
    void deleteTemporaryPasswordValue();
    void disableOne(UUID trackingId);
    boolean globalCredentialNonExpired() throws Exception;
    List<String> getUserRolesListed(UUID userId);
    List<String> getUserScopesListed(UUID userId);
}
