package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.model.ProgramDTO;
import com.jadenx.kxexecutionservice.model.ProgramPatchDTO;
import com.jadenx.kxexecutionservice.service.ProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/programs", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(final ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping
    public ResponseEntity<List<ProgramDTO>> getAllPrograms() {
        return ResponseEntity.ok(programService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramDTO> getProgram(@PathVariable final Long id) {
        return ResponseEntity.ok(programService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createProgram(@RequestBody @Valid final ProgramDTO programDTO) {
        return new ResponseEntity<>(programService.create(programDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProgram(@PathVariable final Long id,
                                              @RequestBody @Valid final ProgramDTO programDTO) {
        programService.update(id, programDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable final Long id) {
        programService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchUpdate(@PathVariable final Long id,
                                            @RequestBody @Valid final ProgramPatchDTO programPatchDTO) {
        programService.patchUpdate(id, programPatchDTO);
        return ResponseEntity.ok().build();
    }
}
