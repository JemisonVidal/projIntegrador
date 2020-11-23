package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.configuration.ApiPageable;
import br.com.house.digital.projetointegrador.model.AbstractEntity;
import br.com.house.digital.projetointegrador.service.BaseService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/api")
@AllArgsConstructor
public abstract class BaseController<T extends AbstractEntity<Long>, S extends BaseService<T, Long>, DTO> {

    protected final S service;

    @GetMapping
    @ApiPageable
    @ApiOperation(value = "Returns a pageable list of all the entities.")
    public ResponseEntity<Page<DTO>> findAll(@RequestParam Optional<String> name, Pageable pageable) {
        final Page<DTO> page = name.map(s -> service.findAllByName(s, pageable))
            .orElse(service.findAll(pageable)).map(this::mapDTO);
        return ResponseEntity.ok(page);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Returns the entity with given ID.")
    public ResponseEntity<DTO> findById(@PathVariable Long id) {
        final T entity = service.findById(id);
        return ResponseEntity.ok().body(mapDTO(entity));
    }

    abstract DTO mapDTO(T entity);

}
