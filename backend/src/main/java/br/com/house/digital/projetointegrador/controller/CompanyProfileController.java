package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.dto.profile.AvatarDTO;
import br.com.house.digital.projetointegrador.dto.profile.CompanyProfileDTO;
import br.com.house.digital.projetointegrador.dto.profile.UpdateAvatarDTO;
import br.com.house.digital.projetointegrador.model.profile.CompanyProfile;
import br.com.house.digital.projetointegrador.service.impl.CompanyProfileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/api/profile/company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyProfileController extends BaseController<CompanyProfile, CompanyProfileService, CompanyProfileDTO> {

    @Autowired
    public CompanyProfileController(CompanyProfileService service) {
        super(service);
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation(value = "Updates the profile with given ID.")
    public ResponseEntity<Void> patch(@RequestBody @Valid CompanyProfileDTO profileDTO, @PathVariable Long id) {
        CompanyProfile profile = service.convertToEntity(profileDTO);
        profile.setId(id);
        service.patch(profile);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}/avatar")
    @ApiOperation(value = "Updates the avatar fields of the profile with given ID.")
    public ResponseEntity<Void> patchAvatar(@RequestBody @Valid UpdateAvatarDTO avatarDTO, @PathVariable Long id) {
        service.patchAvatar(avatarDTO, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/avatar")
    @ApiOperation(value = "Returns the avatar of the profile with given ID.")
    public ResponseEntity<AvatarDTO> findAvatarById(@PathVariable Long id) {
        final String imgSrc = service.findById(id).getImgSrc();
        return ResponseEntity.ok(new AvatarDTO(imgSrc));
    }

    @Override
    CompanyProfileDTO mapDTO(CompanyProfile entity) {
        return service.convertFromEntity(entity, CompanyProfileDTO.class);
    }
}
