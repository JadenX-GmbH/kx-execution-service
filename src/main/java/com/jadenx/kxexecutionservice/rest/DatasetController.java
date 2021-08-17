package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.model.DatasetDTO;
import com.jadenx.kxexecutionservice.service.DatasetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/datasets", produces = MediaType.APPLICATION_JSON_VALUE)
public class DatasetController {

    private final DatasetService datasetService;

    public DatasetController(final DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    @GetMapping
    public ResponseEntity<List<DatasetDTO>> getAllDatasets() {
        return ResponseEntity.ok(datasetService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatasetDTO> getDataset(@PathVariable final Long id) {
        return ResponseEntity.ok(datasetService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createDataset(@RequestBody @Valid final DatasetDTO datasetDTO) {
        return new ResponseEntity<>(datasetService.create(datasetDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDataset(@PathVariable final Long id,
                                              @RequestBody @Valid final DatasetDTO datasetDTO) {
        datasetService.update(id, datasetDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDataset(@PathVariable final Long id) {
        datasetService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
