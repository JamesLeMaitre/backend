package bj.gouv.sineb.authservice.application.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
//@ConfigurationProperties(prefix = "email-service")
public class AuthServiceConfigData {
    @Value("${auth-service.user-creation-email-request-topic-name}")
    private String userCreationEmailRequestTopicName;
    @Value("${auth-service.user-creation-email-response-topic-name}")
    private String userCreationEmailResponseTopicName;
    @Value("${auth-service.ask-new-validation-code-email-request-topic-name}")
    private String askNewValidationCodeEmailRequestTopicName;
    @Value("${auth-service.ask-new-validation-code-email-response-topic-name}")
    private String askNewValidationCodeEmailResponseTopicName;
    @Value("${auth-service.request-email-reset-email-request-topic-name}")
    private String requestEmailResetEmailRequestTopicName;
    @Value("${auth-service.request-email-reset-email-response-topic-name}")
    private String requestEmailResetEmailResponseTopicName;
    @Value("${auth-service.request-password-reset-no-auth-email-request-topic-name}")
    private String requestPasswordResetNoAuthEmailRequestTopicName;
    @Value("${auth-service.request-password-reset-no-auth-email-response-topic-name}")
    private String requestPasswordResetNoAuthEmailResponseTopicName;
    @Value("${auth-service.outbox-scheduler-fixed-rate}")
    private String outboxSchedulerFixedRate;
    @Value("${auth-service.outbox-scheduler-initial-delay}")
    private String outboxSchedulerInitialDelay;
}
