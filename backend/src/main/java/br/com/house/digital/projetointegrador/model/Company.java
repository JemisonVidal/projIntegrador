package br.com.house.digital.projetointegrador.model;

import java.io.Serializable;
import java.time.LocalDate;

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
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String position;
	private String activities;
	private LocalDate initialDate;
	private LocalDate finalDate;
	private boolean acting;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "profile_id")
	private Profile profile;

	public Company() {
	}

	public Company(Integer id, String name, String position, String activities, LocalDate initialDate,
			LocalDate finalDate, boolean acting) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.activities = activities;
		this.initialDate = initialDate;
		this.finalDate = finalDate;
		this.acting = acting;
	}
}
