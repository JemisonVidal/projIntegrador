package br.com.house.digital.projetointegrador.dto.authentication;

import br.com.house.digital.projetointegrador.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    @NotBlank
    @Email(message = "Fill in a valid email")
    private String email;

    @NotBlank(message = "Mandatory Filling")
    @Length(max = 100)
    private String name;

    @NotBlank(message = "Mandatory Filling")
    @Pattern(
        regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[\\w!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{8,}$",
        message = "The password must be 1 uppercase, 1 lowercase, and 1 digit. At least 8 characters."
    )
    private String password;

    @NotNull(message = "Mandatory Filling")
    private UserType type;

}
