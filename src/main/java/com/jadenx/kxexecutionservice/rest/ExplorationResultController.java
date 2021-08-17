package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.model.ExplorationResultDTO;
import com.jadenx.kxexecutionservice.service.ExplorationResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/explorationResults", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExplorationResultController {

    private final ExplorationResultService explorationResultService;

    public ExplorationResultController(final ExplorationResultService explorationResultService) {
        this.explorationResultService = explorationResultService;
    }

    @GetMapping
    public ResponseEntity<List<ExplorationResultDTO>> getAllExplorationResults() {
        return ResponseEntity.ok(explorationResultService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExplorationResultDTO> getExplorationResult(@PathVariable final Long id) {
        return ResponseEntity.ok(explorationResultService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createExplorationResult(
        @RequestBody @Valid final ExplorationResultDTO explorationResultDTO) {
        return new ResponseEntity<>(explorationResultService.create(explorationResultDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateExplorationResult(@PathVariable final Long id,
                                                        @RequestBody
                                                        @Valid final ExplorationResultDTO explorationResultDTO) {
        explorationResultService.update(id, explorationResultDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExplorationResult(@PathVariable final Long id) {
        explorationResultService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
