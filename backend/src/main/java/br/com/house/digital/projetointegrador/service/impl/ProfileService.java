package br.com.house.digital.projetointegrador.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.house.digital.projetointegrador.dto.ProfileDTO;
import br.com.house.digital.projetointegrador.model.Profile;
import br.com.house.digital.projetointegrador.repository.ProfileRepository;
import br.com.house.digital.projetointegrador.service.IService;
import br.com.house.digital.projetointegrador.service.exceptions.DataIntegrityException;
import br.com.house.digital.projetointegrador.service.exceptions.ObjectNotFoundException;

@Service
public class ProfileService implements IService<Profile> {

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public Profile create(Profile object) {
		object.setId(null);
		return profileRepository.save(object);
	}

	@Override
	public Profile findById(Integer id) {
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
		this.findById(object.getId());
		return this.profileRepository.save(object);
	}

	@Override
	public void delete(Integer id) {
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
}
