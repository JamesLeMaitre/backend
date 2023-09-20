package bj.gouv.sineb.emailservice.old.formatter.responses;

import lombok.Data;

import java.io.Serializable;

@Data
public class ValidationFieldResponse implements Serializable {
    private String field;
    private String defaultMessage;
}
