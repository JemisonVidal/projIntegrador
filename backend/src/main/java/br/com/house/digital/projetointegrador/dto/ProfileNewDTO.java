package br.com.house.digital.projetointegrador.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.house.digital.projetointegrador.model.Company;
import br.com.house.digital.projetointegrador.model.Course;
import br.com.house.digital.projetointegrador.model.Skills;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
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

	@NotNull(message = "Mandatory Filling")	
	private Long userId;

	public ProfileNewDTO() {
		
	}
}
