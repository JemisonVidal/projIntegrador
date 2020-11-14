package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.dto.profile.ApplicantProfileDTO;
import br.com.house.digital.projetointegrador.dto.profile.AvatarDTO;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.service.impl.ApplicantProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/api/profile/applicant", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApplicantProfileController {

    private final ApplicantProfileService applicantProfileService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApplicantProfileDTO> findById(@PathVariable Long id) {
        final ApplicantProfileDTO profileDTO = this.applicantProfileService.findByIdWithName(id);
        return ResponseEntity.ok().body(profileDTO);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> patch(@RequestBody @Valid ApplicantProfileDTO profileDTO, @PathVariable Long id) {
        ApplicantProfile profile = applicantProfileService.convertToEntity(profileDTO);
        profile.setId(id);
        applicantProfileService.patch(profileDTO, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.applicantProfileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/avatar")
    public ResponseEntity<AvatarDTO> findAvatarById(@PathVariable Long id) {
        final String imgSrc = applicantProfileService.findById(id).getImgSrc();
        return ResponseEntity.ok(new AvatarDTO(imgSrc));
    }

}
