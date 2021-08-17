package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.model.ExecutionResultDTO;
import com.jadenx.kxexecutionservice.service.ExecutionResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/executionResults", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExecutionResultController {

    private final ExecutionResultService executionResultService;

    public ExecutionResultController(final ExecutionResultService executionResultService) {
        this.executionResultService = executionResultService;
    }

    @GetMapping
    public ResponseEntity<List<ExecutionResultDTO>> getAllExecutionResults() {
        return ResponseEntity.ok(executionResultService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExecutionResultDTO> getExecutionResult(@PathVariable final Long id) {
        return ResponseEntity.ok(executionResultService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createExecutionResult(
        @RequestBody @Valid final ExecutionResultDTO executionResultDTO) {
        return new ResponseEntity<>(executionResultService.create(executionResultDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateExecutionResult(@PathVariable final Long id,
                                                      @RequestBody @Valid final ExecutionResultDTO executionResultDTO) {
        executionResultService.update(id, executionResultDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExecutionResult(@PathVariable final Long id) {
        executionResultService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
