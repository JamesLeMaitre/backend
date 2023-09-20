package bj.gouv.sineb.authservice.application.config;

import bj.gouv.sineb.authservice.application.code.entity.User;
import bj.gouv.sineb.authservice.application.code.repository.UserRepository;
import bj.gouv.sineb.authservice.application.code.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@Component
public class AuthAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserServiceImpl userService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user!=null) {
            List<String> scopes = userService.getUserScopesListed(user.getTrackingId());
            Set<String> authorities = new HashSet<>(Set.of());
            log.info("Authorities value is :"+ authorities.toString());
            log.info("Scopes value is :"+ scopes.toString());
            authorities.addAll(scopes);

            if (passwordEncoder.matches(pwd, user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(email, pwd, getGrantedAuthorities(authorities));
            } else if (passwordEncoder.matches(pwd, user.getTempPwd())) {
                // Dans ce cas ou c'est le tempPwd qui matches alors il le conserve comme password par defaut
                user.setTempPwd(null);
                user.setAccessTokenExpiredAt(null);
                user.setCredentialsNonExpired(true);
                userRepository.save(user);
                return new UsernamePasswordAuthenticationToken(email, pwd, getGrantedAuthorities(authorities));
            } else {
                throw new BadCredentialsException("Invalid password!");
            }
        }else {
            throw new BadCredentialsException("No email registered with this details!");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<String> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
