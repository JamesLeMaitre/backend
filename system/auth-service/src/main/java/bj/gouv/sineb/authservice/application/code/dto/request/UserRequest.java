package bj.gouv.sineb.authservice.application.code.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRequest {
    private UUID trackingId;
    @Email(message = "Email is not correct.")
    private String email;
    @NotNull(message = "Firstname is required.")
    @NotEmpty(message = "Firstname should not be empty.")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Firstname should not contain special characters and space except _.")
    private String firstname;
    @NotNull(message = "Lastname is required.")
    @NotEmpty(message = "Lastname should not be empty.")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Lastname should not contain special characters and space except _.")
    private String lastname;
    @Pattern(regexp = "^(\\+\\d{1,3})?\\s*\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}$", message = "Invalid phone number format")
    private String phone; // like : "+22912345678" or "+1(123)456-7890"
    private String password;
}
