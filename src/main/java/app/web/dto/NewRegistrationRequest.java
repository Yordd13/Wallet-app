package app.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewRegistrationRequest {

    @Email
    private String email;

    @Size(min = 6, message = "Password should be at least 6 symbols long!")
    private String password;

    @Size(min = 6, message = "Username should be at least 6 symbols long!")
    private String username;
}
