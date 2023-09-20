package bj.gouv.sineb.emailservice.old.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class ResponseApi implements Serializable {

    private HttpStatus status;
    private String message;
    private List<?> objects;
    private Object object;
    private boolean success;

    public ResponseApi(HttpStatus status, String message, List<?> objects, boolean success) {
        this.status = status;
        this.message = message;
        this.objects = objects;
        this.success = success;
    }

    public ResponseApi(HttpStatus status, String message, Object object, boolean success) {
        this.status = status;
        this.message = message;
        this.object = object;
        this.success = success;
    }

    public ResponseApi(HttpStatus status, String message, boolean success) {
        this.status = status;
        this.message = message;
        this.success = success;
    }


}
