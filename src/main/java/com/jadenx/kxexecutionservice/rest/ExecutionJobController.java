package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.model.ExecutionJobDTO;
import com.jadenx.kxexecutionservice.service.ExecutionJobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/executionJobs", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExecutionJobController {

    private final ExecutionJobService executionJobService;

    public ExecutionJobController(final ExecutionJobService executionJobService) {
        this.executionJobService = executionJobService;
    }

    @GetMapping
    public ResponseEntity<List<ExecutionJobDTO>> getAllExecutionJobs() {
        return ResponseEntity.ok(executionJobService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExecutionJobDTO> getExecutionJob(@PathVariable final Long id) {
        return ResponseEntity.ok(executionJobService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createExecutionJob(
        @RequestBody @Valid final ExecutionJobDTO executionJobDTO) {
        return new ResponseEntity<>(executionJobService.create(executionJobDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateExecutionJob(@PathVariable final Long id,
                                                   @RequestBody @Valid final ExecutionJobDTO executionJobDTO) {
        executionJobService.update(id, executionJobDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExecutionJob(@PathVariable final Long id) {
        executionJobService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
