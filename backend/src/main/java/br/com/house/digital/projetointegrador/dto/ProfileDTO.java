package br.com.house.digital.projetointegrador.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.com.house.digital.projetointegrador.model.Company;
import br.com.house.digital.projetointegrador.model.Course;
import br.com.house.digital.projetointegrador.model.Profile;
import br.com.house.digital.projetointegrador.model.Skills;
import br.com.house.digital.projetointegrador.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotBlank(message = "Mandatory Filling")
	@Length(min = 5, max = 80, message = "Mandatory filling between 5 to 80 characters")
	private String fullName;
	private String mainFunction;

	@NotBlank(message = "Mandatory Filling")
	@Email(message = "Fill in a valid email")	
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
	private List<Skills> skills = new ArrayList<>();
	private List<Course> courses = new ArrayList<>();
	private List<Company> companys = new ArrayList<>();

	@NotBlank(message = "Mandatory Filling")	
	private User user;

	public ProfileDTO(Profile profile) {
		this.id = profile.getId();
		this.fullName = profile.getFullName();
		this.mainFunction = profile.getMainFunction();
		this.email = profile.getEmail();
		this.salary = profile.getSalary();
		this.telephone = profile.getTelephone();
		this.address = profile.getAddress();
		this.number = profile.getNumber();
		this.neighborhood = profile.getNeighborhood();
		this.city = profile.getCity();
		this.state = profile.getState();
		this.linkedin = profile.getLinkedin();
		this.github = profile.getGithub();
		this.freeText = profile.getFreeText();
		this.user = profile.getUser();
		this.skills = profile.getSkills();
		this.courses = profile.getCourses();
		this.companys = profile.getCompanys();
	}
}
