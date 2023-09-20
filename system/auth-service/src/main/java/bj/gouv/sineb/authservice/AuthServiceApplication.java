package bj.gouv.sineb.authservice;

import bj.gouv.sineb.authservice.application.config.RsakeysConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
        info = @Info(title = "Systeme d'Information National de l'Energie du Benin (SINEB)", version = "1.0.0",
                description = "Toutes les APIs du SINEB",
                termsOfService = "https://www.linkedin.com/in/freddy-j-goudou-5b074618a/", contact = @Contact(name = "Mr Freddy GOUDOU", email = "goudoufreddy@gmail.com"),
                license = @License(name = "", url = ""))
)
@SpringBootApplication
//@ComponentScan(basePackages = {"bj.gouv.sineb.authservice.*", "com.perogroupe.sineb.kafka.*"})
@EnableScheduling
@EnableConfigurationProperties(RsakeysConfig.class)
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
