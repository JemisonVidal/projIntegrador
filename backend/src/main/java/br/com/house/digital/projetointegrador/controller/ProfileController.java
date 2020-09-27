package br.com.house.digital.projetointegrador.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.house.digital.projetointegrador.dto.ProfileDTO;
import br.com.house.digital.projetointegrador.model.Profile;
import br.com.house.digital.projetointegrador.service.impl.ProfileService;

@RestController
@RequestMapping(value = "/v1/api/profile/candidate")
public class ProfileController {

	@Autowired(required = true)
	private ProfileService profileService;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProfileDTO> findById(@PathVariable Integer id) {
		ProfileDTO profileDTO = new ProfileDTO(this.profileService.findById(id));		
		return ResponseEntity.ok().body(profileDTO);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@Valid @RequestBody ProfileDTO profileDTO) {
		Profile profile = profileService.fromDTO(profileDTO);
		profileService.create(profile);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(profile.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Profile profile, @PathVariable Integer id) {
		profile.setId(id);
		profile = profileService.update(profile);
		return ResponseEntity.noContent().build();
	} 
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		this.profileService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
