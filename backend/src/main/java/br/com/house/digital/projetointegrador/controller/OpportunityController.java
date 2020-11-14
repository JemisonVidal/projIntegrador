package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.configuration.ApiPageable;
import br.com.house.digital.projetointegrador.dto.opportunity.NewOpportunityDTO;
import br.com.house.digital.projetointegrador.dto.opportunity.OpportunityDTO;
import br.com.house.digital.projetointegrador.dto.profile.CompanyProfileDTO;
import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import br.com.house.digital.projetointegrador.model.profile.CompanyProfile;
import br.com.house.digital.projetointegrador.service.impl.OpportunityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
        final Opportunity opportunity = this.service.convertToEntity(newOpportunityDTO);
        opportunity.setCompany(user.getProfile());
        final Opportunity created = this.service.save(opportunity);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/applied")
    public ResponseEntity<List<OpportunityDTO>> findAppliedOpportunitiesById(@RequestParam(required = true) Long id) {
        return ResponseEntity.ok(service.findAppliedUsersByProfileId(id));
    }

    @GetMapping("/company/{id}")
    @ApiPageable
    public ResponseEntity<Page<OpportunityDTO>> findAllByCompanyId(@PathVariable Long id, Pageable pageable) {
        final Page<Opportunity> opportunities = this.service.findAllByCompanyId(id, pageable);;
        return ResponseEntity.ok(opportunities.map(this::mapDTO));
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<Void> apply(@PathVariable Long id, @AuthenticationPrincipal User user) {
        this.service.apply(id, user);
        return ResponseEntity.noContent().build();
    }

    @Override
    OpportunityDTO mapDTO(Opportunity entity) {
        return service.mapDTO(entity);
    }
}
