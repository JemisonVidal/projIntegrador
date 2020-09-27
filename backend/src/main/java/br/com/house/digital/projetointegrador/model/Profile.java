package br.com.house.digital.projetointegrador.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Profile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
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
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@JsonManagedReference
	@OneToMany(mappedBy = "profile")
	private List<Skills> skills = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "profile")
	private List<Course> courses = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "profile")
	private List<Company> companys = new ArrayList<>();

	public Profile(Integer id, String fullName, String mainFunction, String email, Double salary, String telephone,
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
