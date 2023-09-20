package bj.gouv.sineb.emailservice;

import bj.gouv.sineb.emailservice.application.code.config.RsakeysConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsakeysConfig.class)
@SpringBootApplication
public class EmailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailServiceApplication.class, args);
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        return Optional.of(new JwtGrantedAuthoritiesConverter())
//                .stream()
//                .peek(jwtAuth -> jwtAuth.setAuthoritiesClaimName("Authorities"))
//                .peek(jwtAuth -> jwtAuth.setAuthorityPrefix(""))
//                .map(jwtAuth -> Optional.of(new JwtAuthenticationConverter())
//                        .stream()
//                        .peek(jwt -> jwt.setJwtGrantedAuthoritiesConverter(jwtAuth))
//                        .findFirst().orElseThrow()
//                ).findFirst().orElseThrow();
//    }

}
