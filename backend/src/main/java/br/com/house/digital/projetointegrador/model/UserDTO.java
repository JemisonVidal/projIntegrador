package br.com.house.digital.projetointegrador.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    @NotBlank
    @Email(message = "Preencha um email válido")
    private String email;

    @NotBlank(message = "Nome não pode estar em branco.")
    private String name;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$",
            message = "A senha precisa ter 1 caractere maiúsculo, 1 minúsculo e 1 digito. Com no mínimo 8 caracteres."
    )
    private String password;

    @NotNull
    private TypeEnum type;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .type(this.type)
                .build();
    }

}
