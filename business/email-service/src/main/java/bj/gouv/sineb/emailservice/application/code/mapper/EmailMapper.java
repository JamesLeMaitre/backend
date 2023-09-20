package bj.gouv.sineb.emailservice.application.code.mapper;

import bj.gouv.sineb.emailservice.application.code.dto.request.EmailRequest;
import bj.gouv.sineb.emailservice.application.code.dto.response.EmailResponse;
import bj.gouv.sineb.emailservice.application.code.entity.Email;
import bj.gouv.sineb.emailservice.application.code.utils.EntityDataCodeGenerationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailMapper {

    private final EntityDataCodeGenerationService entityDataCodeGenerationService;

    public EmailResponse toDto(Email email) {
        EmailResponse response = new EmailResponse();
        BeanUtils.copyProperties(email, response);
        return response;
    }

    public Email toEntity(EmailRequest request) {
        Email email = new Email();
        BeanUtils.copyProperties(request, email);
        return email;
    }

    public Email toEntity(EmailRequest request, Email email) {
        BeanUtils.copyProperties(request, email);
        return email;
    }

}
