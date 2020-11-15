package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.dto.profile.ApplicantProfileDTO;
import br.com.house.digital.projetointegrador.dto.profile.AvatarDTO;
import br.com.house.digital.projetointegrador.dto.profile.UpdateAvatarDTO;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.service.impl.ApplicantProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/api/profile/applicant", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicantProfileController extends BaseController<ApplicantProfile, ApplicantProfileService, ApplicantProfileDTO> {

    @Autowired
    public ApplicantProfileController(ApplicantProfileService service) {
        super(service);
    }

    @Override
    ApplicantProfileDTO mapDTO(ApplicantProfile entity) {
        return this.service.convertFromEntity(entity, ApplicantProfileDTO.class);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> patch(@RequestBody @Valid ApplicantProfileDTO profileDTO, @PathVariable Long id) {
        service.patch(profileDTO, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}/avatar")
    public ResponseEntity<Void> patchAvatar(@RequestBody @Valid UpdateAvatarDTO avatarDTO, @PathVariable Long id) {
        service.patchAvatar(avatarDTO, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/avatar")
    public ResponseEntity<AvatarDTO> findAvatarById(@PathVariable Long id) {
        final String imgSrc = service.findById(id).getImgSrc();
        return ResponseEntity.ok(new AvatarDTO(imgSrc));
    }

}
