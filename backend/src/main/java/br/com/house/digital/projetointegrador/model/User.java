package br.com.house.digital.projetointegrador.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @Type(type="uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @EqualsAndHashCode.Include
    private UUID uuid;

    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(value = EnumType.STRING)
    private TypeEnum type;

}
