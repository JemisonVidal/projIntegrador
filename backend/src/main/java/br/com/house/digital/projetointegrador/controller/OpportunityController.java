package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.dto.OpportunityDTO;
import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.service.impl.OpportunityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v1/api/opportunity")
public class OpportunityController {

    private final OpportunityService opportunityService;

    private final ModelMapper modelMapper;

    @Autowired
    public OpportunityController(OpportunityService opportunityService, ModelMapper modelMapper) {
        this.opportunityService = opportunityService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody OpportunityDTO opportunityDTO, @AuthenticationPrincipal User user) {
        final Opportunity created = this.opportunityService.create(convertToEntity(opportunityDTO, user));
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();
        return ResponseEntity.created(uri).build();
    }

    private Opportunity convertToEntity(OpportunityDTO opportunityDTO) {
        return modelMapper.map(opportunityDTO, Opportunity.class);
    }

    private Opportunity convertToEntity(OpportunityDTO opportunityDTO, User user) {
        final Opportunity opportunity = convertToEntity(opportunityDTO);
        opportunity.setUser(user);
        return opportunity;
    }
}
