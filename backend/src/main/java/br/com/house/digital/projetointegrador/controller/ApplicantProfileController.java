package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.dto.profile.ApplicantProfileDTO;
import br.com.house.digital.projetointegrador.dto.profile.NewApplicantProfileDTO;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.service.impl.ApplicantProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = "/v1/api/profile/applicant", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApplicantProfileController {

    private final ApplicantProfileService applicantProfileService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<NewApplicantProfileDTO> findById(@PathVariable Long id) {
        final ApplicantProfileDTO profileDTO = this.applicantProfileService.findByIdWithName(id);
        return ResponseEntity.ok().body(profileDTO);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody NewApplicantProfileDTO profileDTO, @AuthenticationPrincipal User user) {
        ApplicantProfile profile = applicantProfileService.convertToEntity(profileDTO);
        applicantProfileService.saveWithUser(profile, user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(profile.getId())
            .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody NewApplicantProfileDTO profileDTO, @PathVariable Long id) {
        ApplicantProfile profile = applicantProfileService.convertToEntity(profileDTO);
        profile.setId(id);
        profile = applicantProfileService.update(profile);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.applicantProfileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
