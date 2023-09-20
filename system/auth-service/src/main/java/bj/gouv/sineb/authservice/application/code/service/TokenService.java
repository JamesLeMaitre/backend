package bj.gouv.sineb.authservice.application.code.service;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Validated
public interface TokenService {
    ResponseEntity<Map<String, String>> authenticate(
            String grantType, String email, String password, boolean withRefreshToken, String refreshToken) throws Exception;
}
