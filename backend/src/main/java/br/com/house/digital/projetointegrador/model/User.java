package br.com.house.digital.projetointegrador.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.house.digital.projetointegrador.model.enums.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    
	@OneToOne(mappedBy = "user")
    private Profile profile;	

	public User(UUID uuid, String name, String email, String password, TypeEnum type) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.email = email;
		this.password = password;
		this.type = type;		
	}
}
