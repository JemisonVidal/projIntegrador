package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.dto.ProfileDTO;
import br.com.house.digital.projetointegrador.dto.ProfileNewDTO;
import br.com.house.digital.projetointegrador.model.Profile;
import br.com.house.digital.projetointegrador.service.impl.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/v1/api/profile/candidate")
public class ProfileController {

	private final ProfileService profileService;

	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<ProfileDTO> findById(@PathVariable Long id) {
		ProfileDTO profileDTO = new ProfileDTO(this.profileService.findById(id));		
		return ResponseEntity.ok().body(profileDTO);
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody ProfileNewDTO profileDTO) {
		Profile profile = profileService.fromDTO(profileDTO);
		profileService.create(profile);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(profile.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@RequestBody Profile profile, @PathVariable Integer id) {
		profile.setId(id);
		profile = profileService.update(profile);
		return ResponseEntity.noContent().build();
	} 
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.profileService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
