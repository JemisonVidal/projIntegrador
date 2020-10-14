package br.com.house.digital.projetointegrador.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Profile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private String fullName;
	private String mainFunction;
	private String email;
	private Double salary;
	private String telephone;
	private String address;
	private String number;
	private String neighborhood;
	private String city;
	private String state;
	private String linkedin;
	private String github;
	private String freeText;	
	
	@JsonBackReference
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@JsonManagedReference
	@OneToMany(mappedBy = "profile")
	private List<Skill> skills = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "profile")
	private List<Course> courses = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "profile")
	private List<WorkExperience> workExperiences = new ArrayList<>();

	public Profile(Long id, String fullName, String mainFunction, String email, Double salary, String telephone,
				   String address, String number, String neighborhood, String city, String state, String linkedin,
				   String github, String freeText, User user) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.mainFunction = mainFunction;
		this.email = email;
		this.salary = salary;
		this.telephone = telephone;
		this.address = address;
		this.number = number;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
		this.linkedin = linkedin;
		this.github = github;
		this.freeText = freeText;
		this.user = user;
	}
}
