package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.model.PaginatedResponse;
import com.jadenx.kxexecutionservice.service.DatasetService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private DatasetService datasetService;

    public UserController(final DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    @GetMapping("/datasets")
    public ResponseEntity<PaginatedResponse<?>> getDataSetsByUser(final Principal user, final Pageable pageable) {
        return ResponseEntity.ok(datasetService.findAllByUser(UUID.fromString(user.getName()), pageable));
    }
}
