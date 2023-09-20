package bj.gouv.sineb.authservice.application.code.service.impl;

import bj.gouv.sineb.authservice.application.config.AuthAuthenticationProvider;
import bj.gouv.sineb.authservice.application.code.entity.User;
import bj.gouv.sineb.authservice.application.code.repository.UserRepository;
import bj.gouv.sineb.authservice.application.code.service.GroupeRessourceScopeService;
import bj.gouv.sineb.authservice.application.code.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final AuthAuthenticationProvider authAuthenticationProvider;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final GroupeRessourceScopeService groupeRessourceScopeService;
    private final PasswordEncoder passwordEncoder;

    public TokenServiceImpl(JwtEncoder jwtEncoder,
                            JwtDecoder jwtDecoder,
                            AuthAuthenticationProvider authAuthenticationProvider,
                            UserRepository userRepository, UserServiceImpl userService,
                            GroupeRessourceScopeService groupeRessourceScopeService, PasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.authAuthenticationProvider = authAuthenticationProvider;
        this.userRepository = userRepository;
        this.userService = userService;
        this.groupeRessourceScopeService = groupeRessourceScopeService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public ResponseEntity<Map<String, String>> authenticate(
            String grantType, String email, String password, boolean withRefreshToken, String refreshToken) throws Exception {

        if (grantType!=null && email!=null && password!=null && refreshToken!=null){
            User checkedUser = userRepository.findByEmail(email)
                    .orElseThrow(()-> new RuntimeException("User with this email does not exist. email : "+email));

            if (!passwordEncoder.matches(password, checkedUser.getPassword()) &&
                    !passwordEncoder.matches(password, checkedUser.getTempPwd())){
                throw new RuntimeException("Password of email with {email : "+email+"} is not correct");
            }else {
                String subject=null;
                String scope=null;

                if(grantType.equals("password")){
                    Authentication authentication = authAuthenticationProvider.authenticate(
                            new UsernamePasswordAuthenticationToken(email, password)
                    );
                    subject=authentication.getName();
                    scope=authentication.getAuthorities()
                            .stream().map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(" "));

                } else if(grantType.equals("'refreshToken'")){
                    if(refreshToken==null) {
                        return new ResponseEntity<>(Map.of("errorMessage","Refresh  Token is required"), HttpStatus.UNAUTHORIZED);
                    }
                    Jwt decodeJWT = null;
                    try {
                        decodeJWT = jwtDecoder.decode(refreshToken);
                    } catch (JwtException e) {
                        return new ResponseEntity<>(Map.of("errorMessage",e.getMessage()), HttpStatus.UNAUTHORIZED);
                    }

                    subject=decodeJWT.getSubject();
                    String mySubject = subject;

                    if (subject==null){
                        return new ResponseEntity<>(Map.of("errorMessage", "Invalid Token"), HttpStatus.UNAUTHORIZED);
                    }else {
                        checkedUser = userRepository.findByEmail(subject)
                                .orElseThrow(()-> new Exception("No email with email : "+mySubject));

                        scope = String.join(" ", userService.getUserScopesListed(checkedUser.getId()));
                    }
                }

                Map<String, String> idToken=new HashMap<>();
                Instant instant = Instant.now();
                Instant accessTokenExpiredAt = instant.plus(withRefreshToken?24:148, ChronoUnit.HOURS);
                Instant refreshTokenExpiredAt = instant.plus(148, ChronoUnit.MINUTES);
                JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                        .subject(subject)
                        .issuedAt(instant)
                        .expiresAt(accessTokenExpiredAt)
                        .issuer("sineb-backend")
                        .claim("scope",scope)
                        .build();

                String jwtAccessToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

                idToken.put("accessToken",jwtAccessToken);

                if(withRefreshToken){
                    JwtClaimsSet jwtClaimsSetRefresh=JwtClaimsSet.builder()
                            .subject(subject)
                            .issuedAt(instant)
                            .expiresAt(refreshTokenExpiredAt)
                            .issuer("email-service")
                            .build();
                    String jwtRefreshToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetRefresh)).getTokenValue();
                    idToken.put("refreshToken",jwtRefreshToken);
                }
                checkedUser.setAccessTokenExpiredAt(accessTokenExpiredAt);
                userRepository.save(checkedUser);
                return new ResponseEntity<>(idToken,HttpStatus.OK);
            }
        }else {
            HashMap<String, String> errorValues = new HashMap<>();
            errorValues.put("message", "None of these properties must be null.");
            errorValues.put("grantType", grantType);
            errorValues.put("email", email);
            errorValues.put("password", password);
            errorValues.put("refreshToken", refreshToken);

            return ResponseEntity.badRequest().body(errorValues);
        }
    }
}
