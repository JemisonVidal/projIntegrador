package br.com.house.digital.projetointegrador.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.UserType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@Builder
public class UserDTO {

    @NotBlank
    @Email(message = "Fill in a valid email")
    private final String email;

    @NotBlank(message = "Mandatory Filling")
    private final String name;

    @NotBlank(message = "Mandatory Filling")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$",
            message = "The password must be 1 uppercase, 1 lowercase, and 1 digit. At least 8 characters."
    )
    private final String password;

    @NotNull(message = "Mandatory Filling")
    private final UserType type;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
        		.name(this.name)
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .type(type)
                .build();
    }

}
