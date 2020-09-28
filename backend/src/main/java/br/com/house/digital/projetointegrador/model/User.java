package br.com.house.digital.projetointegrador.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)	   
    @EqualsAndHashCode.Include
    private Integer id;

    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(value = EnumType.STRING)
    private TypeEnum type;    
    
	@OneToOne(mappedBy = "user")
    private Profile profile;	

	public User(Integer id, String name, String email, String password, TypeEnum type) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.type = type;		
	}
}
