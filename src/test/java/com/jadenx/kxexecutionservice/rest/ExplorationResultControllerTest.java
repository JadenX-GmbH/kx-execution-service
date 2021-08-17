package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.config.BaseIT;
import com.jadenx.kxexecutionservice.model.ErrorResponse;
import com.jadenx.kxexecutionservice.model.ExplorationResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ExplorationResultControllerTest extends BaseIT {

    @Test
    @Sql({"/data/gigData.sql", "/data/explorationJobData.sql", "/data/explorationResultData.sql"})
    public void getAllExplorationResults_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<List<ExplorationResultDTO>> response = restTemplate.exchange(
            "/api/explorationResults", HttpMethod.GET, request, new ParameterizedTypeReference<>() {
            });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1300L, response.getBody().get(0).getId());
    }

    @Test
    @Sql({"/data/gigData.sql", "/data/explorationJobData.sql", "/data/explorationResultData.sql"})
    public void getExplorationResult_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ExplorationResultDTO> response = restTemplate.exchange(
            "/api/explorationResults/1300", HttpMethod.GET, request, ExplorationResultDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Aenean pulvinar...", response.getBody().getLocation());
    }

    @Test
    public void getExplorationResult_notFound() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/explorationResults/1966", HttpMethod.GET, request, ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ResponseStatusException", response.getBody().getException());
    }

    @Test
    @Sql({"/data/gigData.sql", "/data/explorationJobData.sql"})
    public void createExplorationResult_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/explorationResultDTORequest.json"), headers());
        final ResponseEntity<Long> response = restTemplate.exchange(
            "/api/explorationResults", HttpMethod.POST, request, Long.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, explorationResultRepository.count());
    }

    @Test
    public void createExplorationResult_missingField() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/explorationResultDTORequest_missingField.json"), headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/explorationResults", HttpMethod.POST, request, ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("MethodArgumentNotValidException", response.getBody().getException());
        assertEquals("location", response.getBody().getFieldErrors().get(0).getField());
    }

    @Test
    @Sql({"/data/gigData.sql", "/data/explorationJobData.sql", "/data/explorationResultData.sql"})
    public void updateExplorationResult_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/explorationResultDTORequest.json"), headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/explorationResults/1300", HttpMethod.PUT, request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ut pellentesque sapien...",
            explorationResultRepository.findById(1300L).get().getLocation());
    }

    @Test
    @Sql({"/data/gigData.sql", "/data/explorationJobData.sql", "/data/explorationResultData.sql"})
    public void deleteExplorationResult_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/explorationResults/1300", HttpMethod.DELETE, request, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, explorationResultRepository.count());
    }

}
