package bj.gouv.sineb.emailservice.application.code.service;

import bj.gouv.sineb.emailservice.application.code.dto.request.EmailRequest;
import bj.gouv.sineb.emailservice.application.code.dto.response.EmailResponse;

public interface EmailService {
    EmailResponse send(EmailRequest request);

}
