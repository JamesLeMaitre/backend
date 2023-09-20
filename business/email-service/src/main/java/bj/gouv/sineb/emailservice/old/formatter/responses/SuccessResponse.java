package bj.gouv.sineb.emailservice.old.formatter.responses;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class SuccessResponse extends HttpResponse implements Serializable {
    private Object data;
    private boolean success;
}
