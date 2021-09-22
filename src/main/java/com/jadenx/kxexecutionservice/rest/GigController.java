package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.model.*;
import com.jadenx.kxexecutionservice.service.DatasetService;
import com.jadenx.kxexecutionservice.service.GigService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(value = "/api/gigs", produces = MediaType.APPLICATION_JSON_VALUE)
public class GigController {

    private final GigService gigService;
    private final DatasetService datasetService;

    public GigController(final GigService gigService, final DatasetService datasetService) {
        this.gigService = gigService;
        this.datasetService = datasetService;
    }

    @GetMapping
    public ResponseEntity<List<GigDTO>> getAllGigs(final Principal user) {
        return ResponseEntity.ok(gigService.findAll(user.getName()));
    }

    @GetMapping("/{id}/datasets")
    public ResponseEntity<PaginatedResponse<?>> getDatasetsByGig(@PathVariable final Long id,
                                                                 final Principal user, final Pageable pageable) {
        return ResponseEntity.ok(datasetService.findAllByGig(id, UUID.fromString(user.getName()), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GigDTO> getGig(@PathVariable final Long id, final Principal user) {
        return ResponseEntity.ok(gigService.get(id, user.getName()));
    }

    @GetMapping("/{id}/executionJobs")
    public ResponseEntity<List<ExecutionJobDTO>> getExecutionJobsForGig(
        @PathVariable final Long id, final Principal user ) {
        return ResponseEntity.ok(gigService.getExecutionJobs(id, user.getName()));
    }

    @GetMapping("/{id}/explorationJobs")
    public ResponseEntity<List<ExplorationJobDTO>> getExplorationJobsForGig(
        @PathVariable final Long id, final Principal user ) {
        return ResponseEntity.ok(gigService.getExplorationJobs(id, user.getName()));
    }


    @PostMapping
    public ResponseEntity<Long> createGig(@RequestBody @Valid final GigDTO gigDTO) {
        return new ResponseEntity<>(gigService.create(gigDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateGig(@PathVariable final Long id,
                                          @RequestBody @Valid final GigDTO gigDTO) {
        gigService.update(id, gigDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGig(@PathVariable final Long id) {
        gigService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/gigId")
    public ResponseEntity<Void> updateByGigId(@PathVariable final Long id,
                                              @RequestBody @Valid final GigDTO gigDTO) {
        gigService.update(id, gigDTO);
        return ResponseEntity.ok().build();
    }
}
