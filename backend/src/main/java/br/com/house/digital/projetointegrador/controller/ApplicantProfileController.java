package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.configuration.ApiPageable;
import br.com.house.digital.projetointegrador.dto.profile.ApplicantProfileDTO;
import br.com.house.digital.projetointegrador.dto.profile.AvatarDTO;
import br.com.house.digital.projetointegrador.dto.profile.UpdateAvatarDTO;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.service.impl.ApplicantProfileService;
import br.com.house.digital.projetointegrador.service.specification.ApplicantProfileSpecification;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/api/profile/applicant", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicantProfileController extends
    BaseController<ApplicantProfile, ApplicantProfileService, ApplicantProfileDTO> {

    @Autowired
    public ApplicantProfileController(ApplicantProfileService service) {
        super(service);
    }

    @Override
    ApplicantProfileDTO mapDTO(ApplicantProfile entity) {
        return this.service.convertFromEntity(entity, ApplicantProfileDTO.class);
    }

    @GetMapping
    @ApiPageable
    @ApiOperation(value = "Returns a pageable list of all the applicants profiles matching the parameters.")
    public ResponseEntity<Page<ApplicantProfileDTO>> findAll(
        @RequestParam Optional<String> name,
        @RequestParam Optional<List<String>> skills,
        @RequestParam Optional<List<String>> courses,
        Pageable pageable
    ) {
        List<Specification<ApplicantProfile>> specifications = new ArrayList<>();
        name.ifPresent(s -> specifications.add(ApplicantProfileSpecification.nameContains(s)));
        skills.ifPresent(list -> specifications.addAll(
            getSpecificationsFromList(list, ApplicantProfileSpecification::skillContains)));
        courses.ifPresent(list -> specifications.addAll(
            getSpecificationsFromList(list, ApplicantProfileSpecification::courseContains)));
        return ResponseEntity.ok(doSearch(specifications, pageable).map(this::mapDTO));
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation(value = "Updates the profile with given ID.")
    public ResponseEntity<Void> patch(@RequestBody @Valid ApplicantProfileDTO profileDTO, @PathVariable Long id) {
        service.patch(profileDTO, id);
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

}
