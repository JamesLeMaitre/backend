package bj.gouv.sineb.demandeservice.application.code.config;



import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public record RsakeysConfig(RSAPublicKey publicKey) {
}
