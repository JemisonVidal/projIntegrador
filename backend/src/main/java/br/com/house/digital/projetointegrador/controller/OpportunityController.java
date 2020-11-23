package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.dto.opportunity.NewOpportunityDTO;
import br.com.house.digital.projetointegrador.dto.opportunity.OpportunityDTO;
import br.com.house.digital.projetointegrador.dto.profile.ApplicantProfileDTO;
import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.service.impl.OpportunityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/api/opportunity", produces = MediaType.APPLICATION_JSON_VALUE)
public class OpportunityController extends BaseController<Opportunity, OpportunityService, OpportunityDTO> {

    @Autowired
    public OpportunityController(OpportunityService service) {
        super(service);
    }

    @PostMapping
    @ApiOperation(value = "Create a new opportunity with the authenticated company.")
    public ResponseEntity<Void> create(@Valid @RequestBody NewOpportunityDTO newOpportunityDTO,
                                       @AuthenticationPrincipal User user) {
        final Opportunity created = this.service.save(newOpportunityDTO, user);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/applied")
    @ApiOperation(value = "Finds all the applied opportunities for the authenticated user.")
    public ResponseEntity<List<OpportunityDTO>> findUserAppliedOpportunities(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.findAppliedOpportunitiesByProfileId(user.getProfileId()));
    }

    @GetMapping("/company/{id}")
    @ApiOperation(value = "Finds all opportunities for the given company profile ID.")
    public ResponseEntity<List<OpportunityDTO>> findAllByCompanyId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findAllByCompanyId(id));
    }

    @PostMapping("/{id}/apply")
    @ApiOperation(value = "Applies the authenticated user to the opportunity with given ID.")
    public ResponseEntity<Void> apply(@PathVariable Long id, @AuthenticationPrincipal User user) {
        this.service.apply(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/applied")
    @ApiOperation(value = "Finds the users applied to the opportunity with given ID.")
    public ResponseEntity<List<ApplicantProfileDTO>> findAppliedUsersByOpportunityId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findAppliedUsersByOpportunityId(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes the opportunity with given ID.")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/active")
    @ApiOperation(value = "Toggles the active field of the opportunity with given ID.")
    public ResponseEntity<Void> toggleActive(@PathVariable Long id) {
        service.toggleActive(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Updates the opportunity with given ID.")
    public ResponseEntity<OpportunityDTO> patchOpportunityById(@PathVariable Long id, @Valid @RequestBody NewOpportunityDTO dto) {
        Opportunity opportunity = service.patch(id, dto);
        return ResponseEntity.ok(this.mapDTO(opportunity));
    }

    @Override
    OpportunityDTO mapDTO(Opportunity entity) {
        return service.mapDTO(entity);
    }
}
