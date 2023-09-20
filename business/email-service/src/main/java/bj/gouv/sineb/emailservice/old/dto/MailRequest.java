package bj.gouv.sineb.emailservice.old.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MailRequest implements Serializable {
    @NotNull(message = "Insert receiver mail")
    @Email(message = "Mail not valid", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private String mailTo;
    @NotNull(message = "Insert sender mail")
    private String mailFrom;
    @NotNull(message = "Insert receiver name")
    private String name;
    @NotNull(message = "Insert the subject")
    private String subject;
    @NotNull(message = "Insert the text")
    private String text;
    private String attachment;
}
