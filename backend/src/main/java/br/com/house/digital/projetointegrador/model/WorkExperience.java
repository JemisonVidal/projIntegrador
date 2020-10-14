package br.com.house.digital.projetointegrador.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WorkExperience implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
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

	public WorkExperience(Long id, String name, String position, String activities, LocalDate initialDate,
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
