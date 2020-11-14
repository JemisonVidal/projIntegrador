package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.dto.opportunity.NewOpportunityDTO;
import br.com.house.digital.projetointegrador.dto.opportunity.OpportunityDTO;
import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.service.impl.OpportunityService;
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
@RequestMapping(path = "/v1/api/opportunity", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class OpportunityController {

    private final OpportunityService opportunityService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody NewOpportunityDTO newOpportunityDTO,
                                       @AuthenticationPrincipal User user) {
        final Opportunity opportunity = this.opportunityService.convertToEntity(newOpportunityDTO);
        opportunity.setCompany(user.getProfile());
        final Opportunity created = this.opportunityService.save(opportunity);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<OpportunityDTO>> findAll(Pageable pageable) {
        final Page<OpportunityDTO> page = opportunityService.findAll(pageable)
            .map(opportunity -> opportunityService.convertFromEntity(opportunity, OpportunityDTO.class));
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpportunityDTO> findById(@PathVariable Long id) {
        final Opportunity opportunity = this.opportunityService.findById(id);
        final OpportunityDTO dto = this.opportunityService.convertFromEntity(opportunity, OpportunityDTO.class);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<Page<OpportunityDTO>> findAllByCompanyId(@PathVariable Long id, Pageable pageable) {
        final Page<Opportunity> opportunities = this.opportunityService.findAllByCompanyId(id, pageable);
        Page<OpportunityDTO> page = opportunities
            .map(opportunity -> opportunityService.convertFromEntity(opportunity, OpportunityDTO.class));
        return ResponseEntity.ok(page);
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<Void> apply(@PathVariable Long id, @AuthenticationPrincipal User user) {
        this.opportunityService.apply(id, user);
        return ResponseEntity.noContent().build();
    }

}
