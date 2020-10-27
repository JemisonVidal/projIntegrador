package br.com.house.digital.projetointegrador.dto.authentication;

import br.com.house.digital.projetointegrador.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@AllArgsConstructor
public class RegisterDTO {

    @NotBlank
    @Email(message = "Fill in a valid email") String email;

    @NotBlank(message = "Mandatory Filling") String name;

    @NotBlank(message = "Mandatory Filling")
    @Pattern(
        regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$",
        message = "The password must be 1 uppercase, 1 lowercase, and 1 digit. At least 8 characters."
    ) String password;

    @NotNull(message = "Mandatory Filling") UserType type;

}
