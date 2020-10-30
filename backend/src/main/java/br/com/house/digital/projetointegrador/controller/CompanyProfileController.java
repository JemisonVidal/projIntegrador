package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.dto.profile.CompanyProfileDTO;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.profile.CompanyProfile;
import br.com.house.digital.projetointegrador.service.impl.CompanyProfileService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = "/v1/api/profile/company", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CompanyProfileController {

    private final CompanyProfileService companyProfileService;

    @GetMapping
    public ResponseEntity<Page<CompanyProfileDTO>> findAll(Pageable pageable) {
        final Page<CompanyProfileDTO> profiles = this.companyProfileService.findAll(pageable)
            .map(companyProfile -> companyProfileService.convertFromEntity(companyProfile, CompanyProfileDTO.class));
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyProfileDTO> findById(@PathVariable Long id) {
        final CompanyProfile profile = this.companyProfileService.findById(id);
        final CompanyProfileDTO profileDTO = this.companyProfileService.convertFromEntity(profile,
            CompanyProfileDTO.class);
        return ResponseEntity.ok(profileDTO);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CompanyProfileDTO profileDTO,
                                       @AuthenticationPrincipal User user) {
        CompanyProfile profile = companyProfileService.convertToEntity(profileDTO);
        companyProfileService.saveWithUser(profile, user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(profile.getId())
            .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody CompanyProfileDTO profileDTO, @PathVariable Long id) {
        CompanyProfile profile = companyProfileService.convertToEntity(profileDTO);
        profile.setId(id);
        profile = companyProfileService.update(profile);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.companyProfileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
