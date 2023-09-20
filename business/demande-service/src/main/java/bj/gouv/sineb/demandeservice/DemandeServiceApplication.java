package bj.gouv.sineb.demandeservice;

import bj.gouv.sineb.demandeservice.application.code.config.RsakeysConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@EnableConfigurationProperties(RsakeysConfig.class)
@SpringBootApplication
public class DemandeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemandeServiceApplication.class, args);
	}


}
