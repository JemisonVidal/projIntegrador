package br.com.house.digital.projetointegrador.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.house.digital.projetointegrador.dto.ProfileDTO;
import br.com.house.digital.projetointegrador.dto.ProfileNewDTO;
import br.com.house.digital.projetointegrador.model.Company;
import br.com.house.digital.projetointegrador.model.Course;
import br.com.house.digital.projetointegrador.model.Profile;
import br.com.house.digital.projetointegrador.model.Skills;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.repository.CompanyRepository;
import br.com.house.digital.projetointegrador.repository.CourseRepository;
import br.com.house.digital.projetointegrador.repository.ProfileRepository;
import br.com.house.digital.projetointegrador.repository.SkillsRepository;
import br.com.house.digital.projetointegrador.repository.UserRepository;
import br.com.house.digital.projetointegrador.service.IService;
import br.com.house.digital.projetointegrador.service.exceptions.DataIntegrityException;
import br.com.house.digital.projetointegrador.service.exceptions.ObjectNotFoundException;

@Service
public class ProfileService implements IService<Profile> {

	private final ProfileRepository profileRepository;

	private final UserRepository userRepository;

	private final SkillsRepository skillsRepository;

	private final CourseRepository coursesRepository;

	private final CompanyRepository companyRepository;

	@Autowired
	public ProfileService(ProfileRepository profileRepository, UserRepository userRepository, SkillsRepository skillsRepository, CourseRepository coursesRepository, CompanyRepository companyRepository) {
		this.profileRepository = profileRepository;
		this.userRepository = userRepository;
		this.skillsRepository = skillsRepository;
		this.coursesRepository = coursesRepository;
		this.companyRepository = companyRepository;
	}

	@Transactional
	@Override
	public Profile create(Profile object) {
		object.setId(null);
		object = profileRepository.save(object);
		skillsRepository.saveAll(object.getSkills());
		coursesRepository.saveAll(object.getCourses());
		companyRepository.saveAll(object.getCompanies());
		return object;
	}

	@Override
	public Profile findById(Long id) {
		Optional<Profile> obj = profileRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", type: " + Profile.class.getName()));
	}

	@Override
	public List<Profile> findAll() {
		return this.profileRepository.findAll();
	}

	@Override
	public Profile update(Profile object) {
		Profile newObject = this.findById(object.getId());
		this.updateData(newObject, object);
		return this.profileRepository.save(newObject);
	}

	@Override
	public void delete(Long id) {
		this.findById(id);
		try {
			this.profileRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("It was not possible to delete the profile");
		}
	}

	public Profile fromDTO(ProfileDTO profileDTO) {
		return new Profile(profileDTO.getId(), profileDTO.getFullName(), profileDTO.getMainFunction(),
				profileDTO.getEmail(), profileDTO.getSalary(), profileDTO.getTelephone(), profileDTO.getAddress(),
				profileDTO.getNumber(), profileDTO.getNeighborhood(), profileDTO.getCity(), profileDTO.getState(),
				profileDTO.getLinkedin(), profileDTO.getGithub(), profileDTO.getFreeText(), profileDTO.getUser());
	}

	public Profile fromDTO(ProfileNewDTO profileNewDTO) {
		Optional<User> userOptional = userRepository.findById(profileNewDTO.getUserId());
		User user = userOptional.get();
		Profile profile = new Profile(null, profileNewDTO.getFullName(), profileNewDTO.getMainFunction(),
				profileNewDTO.getEmail(), profileNewDTO.getSalary(), profileNewDTO.getTelephone(),
				profileNewDTO.getAddress(), profileNewDTO.getNumber(), profileNewDTO.getNeighborhood(),
				profileNewDTO.getCity(), profileNewDTO.getState(), profileNewDTO.getLinkedin(),
				profileNewDTO.getGithub(), profileNewDTO.getFreeText(), user);

		List<Skills> listSkills = new ArrayList<>();
		for (Skills skill : profileNewDTO.getSkills()) {
			listSkills.add(
					new Skills(null, skill.getName(), skill.getExperienceTime(), skill.getKnowledgeLevel().getId(), profile));
		}

		List<Course> listCourse = new ArrayList<>();
		for (Course course : profileNewDTO.getCourses()) {
			listCourse.add(new Course(null, course.getInstitution(), course.getName(), course.getWorkLoad(),
					course.getConclusionYear(), profile));
		}

		List<Company> listCompanies = new ArrayList<>();
		for (Company company : profileNewDTO.getCompanies()) {
			listCompanies.add(new Company(null, company.getName(), company.getPosition(), company.getActivities(),
					company.getInitialDate(), company.getFinalDate(), company.isActing()));
		}

		profile.getSkills().addAll(listSkills);
		profile.getCourses().addAll(listCourse);
		profile.getCompanies().addAll(listCompanies);
		return profile;
	}

	private void updateData(Profile newObject, Profile object) {
		newObject.setId(object.getId());
		newObject.setFullName(object.getFullName());
		newObject.setMainFunction(object.getMainFunction());
		newObject.setEmail(object.getEmail());
		newObject.setSalary(object.getSalary());
		newObject.setTelephone(object.getTelephone());
		newObject.setAddress(object.getAddress());
		newObject.setNumber(object.getNumber());
		newObject.setNeighborhood(object.getNeighborhood());
		newObject.setCity(object.getCity());
		newObject.setState(object.getState());
		newObject.setLinkedin(object.getLinkedin());
		newObject.setGithub(object.getGithub());
		newObject.setFreeText(object.getFreeText());
		newObject.setUser(object.getUser());
		newObject.setSkills(object.getSkills());
		newObject.setCourses(object.getCourses());
		newObject.setCompanies(object.getCompanies());
	}
}
