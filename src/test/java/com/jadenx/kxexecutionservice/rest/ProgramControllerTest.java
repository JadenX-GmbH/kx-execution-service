package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.config.BaseIT;
import com.jadenx.kxexecutionservice.model.ErrorResponse;
import com.jadenx.kxexecutionservice.model.ProgramDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProgramControllerTest extends BaseIT {

    @Test
    @Sql({"/data/gigData.sql", "/data/executionJobData.sql", "/data/programData.sql"})
    public void getAllPrograms_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<List<ProgramDTO>> response = restTemplate.exchange(
            "/api/programs", HttpMethod.GET, request, new ParameterizedTypeReference<List<ProgramDTO>>() {
            });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1600L, response.getBody().get(0).getId());
    }

    @Test
    @Sql({"/data/gigData.sql", "/data/executionJobData.sql", "/data/programData.sql"})
    public void getProgram_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ProgramDTO> response = restTemplate.exchange(
            "/api/programs/1600", HttpMethod.GET, request, ProgramDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ut pellentesque sapien...", response.getBody().getHash());
    }

    @Test
    public void getProgram_notFound() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/programs/2266", HttpMethod.GET, request, ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ResponseStatusException", response.getBody().getException());
    }

    @Test
    @Sql({"/data/gigData.sql", "/data/executionJobData.sql"})
    public void createProgram_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/programDTORequest.json"), headers());
        final ResponseEntity<Long> response = restTemplate.exchange(
            "/api/programs", HttpMethod.POST, request, Long.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, programRepository.count());
    }

    @Test
    public void createProgram_missingField() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/programDTORequest_missingField.json"), headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/programs", HttpMethod.POST, request, ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("MethodArgumentNotValidException", response.getBody().getException());
        assertEquals("hash", response.getBody().getFieldErrors().get(0).getField());
    }

    @Test
    @Sql({"/data/gigData.sql", "/data/executionJobData.sql", "/data/programData.sql"})
    public void updateProgram_success() {
        final HttpEntity<String> request = new HttpEntity<>(readResource(
            "/requests/programDTORequest.json"), headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/programs/1600", HttpMethod.PUT, request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cras sed interdum...", programRepository.findById(1600L).get().getHash());
    }

    @Test
    @Sql({"/data/gigData.sql", "/data/executionJobData.sql", "/data/programData.sql"})
    public void deleteProgram_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/programs/1600", HttpMethod.DELETE, request, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, programRepository.count());
        assertEquals(1,executionJobRepository.count());
    }

}
