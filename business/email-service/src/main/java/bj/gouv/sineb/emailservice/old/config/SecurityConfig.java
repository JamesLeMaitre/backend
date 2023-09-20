//package bj.gouv.sineb.emailservice.old.config;
//
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
//import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
//import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//@Configuration
//@EnableWebSecurity
//@AllArgsConstructor
//@EnableMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//    private final KeyUtils keyUtils;
//    private final JwtAuthenticationConverter converter;
//
//    @Bean
//    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorization -> authorization.anyRequest().authenticated())
//                .oauth2ResourceServer(oAuth2 -> oAuth2.jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(converter)))
//                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(new BearerTokenAccessDeniedHandler()).authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()))
//                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
//    }
//
//    @Bean
//    @Primary
//    public JwtDecoder jwtAccessTokenDecoder() {
//        return NimbusJwtDecoder.withPublicKey(keyUtils.getAccessTokenPublicKey()).build();
//    }
//
//    @Bean
//    @Qualifier("jwtRefreshTokenDecoder")
//    public JwtDecoder jwtRefreshTokenDecoder() {
//        return NimbusJwtDecoder.withPublicKey(keyUtils.getRefreshTokenPublicKey()).build();
//    }
//
//    @Bean
//    @Qualifier("JwtRefreshAuthenticationProvider")
//    public JwtAuthenticationProvider jwtAuthenticationProvider() {
//        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtRefreshTokenDecoder());
//        provider.setJwtAuthenticationConverter(converter);
//        return provider;
//    }
//
//}
