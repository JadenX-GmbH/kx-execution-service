package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.model.ExplorationJobDTO;
import com.jadenx.kxexecutionservice.service.ExplorationJobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/explorationJobs", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExplorationJobController {

    private final ExplorationJobService explorationJobService;

    public ExplorationJobController(final ExplorationJobService explorationJobService) {
        this.explorationJobService = explorationJobService;
    }

    @GetMapping
    public ResponseEntity<List<ExplorationJobDTO>> getAllExplorationJobs() {
        return ResponseEntity.ok(explorationJobService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExplorationJobDTO> getExplorationJob(@PathVariable final Long id) {
        return ResponseEntity.ok(explorationJobService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createExplorationJob(
        @RequestBody @Valid final ExplorationJobDTO explorationJobDTO) {
        return new ResponseEntity<>(explorationJobService.create(explorationJobDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateExplorationJob(@PathVariable final Long id,
                                                     @RequestBody @Valid final ExplorationJobDTO explorationJobDTO) {
        explorationJobService.update(id, explorationJobDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExplorationJob(@PathVariable final Long id) {
        explorationJobService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
