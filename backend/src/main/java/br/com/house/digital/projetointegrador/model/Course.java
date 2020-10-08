package br.com.house.digital.projetointegrador.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String institution;
	private String name;
	private String workLoad;
	private Integer conclusionYear;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "profile_id")
	private Profile profile;

	public Course() {
	}

	public Course(Integer id, String institution, String name, String workLoad, Integer conclusionYear,
			Profile profile) {
		super();
		this.id = id;
		this.institution = institution;
		this.name = name;
		this.workLoad = workLoad;
		this.conclusionYear = conclusionYear;
		this.profile = profile;
	}

}
