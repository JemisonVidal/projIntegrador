package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.dto.opportunity.NewOpportunityDTO;
import br.com.house.digital.projetointegrador.dto.opportunity.OpportunityDTO;
import br.com.house.digital.projetointegrador.dto.profile.ApplicantProfileDTO;
import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.service.impl.OpportunityService;
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
    public ResponseEntity<List<OpportunityDTO>> findAppliedOpportunitiesByProfileId(@RequestParam Long id) {
        return ResponseEntity.ok(service.findAppliedOpportunitiesByProfileId(id));
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<OpportunityDTO>> findAllByCompanyId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findAllByCompanyId(id));
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<Void> apply(@PathVariable Long id, @AuthenticationPrincipal User user) {
        this.service.apply(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/applied")
    public ResponseEntity<List<ApplicantProfileDTO>> findAppliedUsersByOpportunityId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findAppliedUsersByOpportunityId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Void> toggleActive(@PathVariable Long id) {
        service.toggleActive(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OpportunityDTO> patchOpportunityById(@PathVariable Long id, @Valid @RequestBody NewOpportunityDTO dto) {
        Opportunity opportunity = service.patch(id, dto);
        return ResponseEntity.ok(this.mapDTO(opportunity));
    }

    @Override
    OpportunityDTO mapDTO(Opportunity entity) {
        return service.mapDTO(entity);
    }
}
