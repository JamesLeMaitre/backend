package bj.gouv.sineb.authservice.application.code.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TrackAllRequest {


    @Min(value = 0, message = "page should be greater than or equal to 0")
    int page = 0;
    @Min(value = 1, message = "size should be greater than or equal to 1")
    int size = 20;
    boolean deleted = false;
    boolean expired = false;
}
