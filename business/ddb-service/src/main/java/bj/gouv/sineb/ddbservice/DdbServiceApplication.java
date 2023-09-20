package bj.gouv.sineb.ddbservice;

import bj.gouv.sineb.ddbservice.application.code.config.RsakeysConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsakeysConfig.class)
@SpringBootApplication
public class DdbServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdbServiceApplication.class, args);
	}

}
