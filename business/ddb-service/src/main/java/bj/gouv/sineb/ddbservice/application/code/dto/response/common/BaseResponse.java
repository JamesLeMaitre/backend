package bj.gouv.sineb.ddbservice.application.code.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    private int status;
    private String message;
    private T data;
}
