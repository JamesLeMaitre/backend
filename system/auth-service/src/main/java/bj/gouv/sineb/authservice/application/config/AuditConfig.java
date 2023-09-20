package bj.gouv.sineb.authservice.application.config;

import bj.gouv.sineb.authservice.application.code.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {

    private final UserRepository userRepository;

    public AuditConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    AuditorAware<String> auditorProvider(){
        return new AuditorAwareImpl(userRepository);
    }
}
