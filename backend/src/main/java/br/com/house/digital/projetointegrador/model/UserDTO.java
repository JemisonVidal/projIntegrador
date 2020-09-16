package br.com.house.digital.projetointegrador.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class UserDTO {

    @NotBlank
    @Email(message = "Preencha um email válido")
    private final String email;

    @NotBlank(message = "Nome não pode estar em branco.")
    private final String name;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$",
            message = "A senha precisa ter 1 caractere maiúsculo, 1 minúsculo e 1 digito. Com no mínimo 8 caracteres."
    )
    private final String password;

    @NotNull
    private final String type;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .type(TypeEnum.valueOf(this.type))
                .build();
    }

}
