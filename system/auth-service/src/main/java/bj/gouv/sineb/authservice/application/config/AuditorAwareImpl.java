package bj.gouv.sineb.authservice.application.config;

import bj.gouv.sineb.authservice.application.constant.AppAuthConstant;
import bj.gouv.sineb.authservice.application.interceptor.HttpRequestResponseUtils;
import bj.gouv.sineb.authservice.application.code.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    private final UserRepository userRepository;

    public AuditorAwareImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Optional<String> getCurrentAuditor() {
        String email = HttpRequestResponseUtils.getLoggedInUserName();
        log.info("AuditorAwareImpl email : {}", email);
        //UUID userId = userService.getOneUserIdWithEmail(email);
        //Optional<User> userOptional = userRepository.findByEmail(email);
        /*if (userOptional.isEmpty()){
            log.info("Using system@system.com userId");
            return Optional.of(AppConstant.SYSTEM_USER_ID.toString());
        }
        UUID userId = userOptional.get().getId();*/
        //log.info("AuditorAwareImpl userId : {}", userId);
        //return Optional.of(userId.toString());
        //String email = HttpRequestResponseUtils.getLoggedInUserName();
        return Optional.of(email).or(()->Optional.of(AppAuthConstant.SYSTEM_USER_EMAIL));
    }
}
