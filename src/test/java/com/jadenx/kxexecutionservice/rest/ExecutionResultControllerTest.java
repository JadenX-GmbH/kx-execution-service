package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.config.BaseIT;
import com.jadenx.kxexecutionservice.model.ErrorResponse;
import com.jadenx.kxexecutionservice.model.ExecutionResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ExecutionResultControllerTest extends BaseIT {

    @Test
    @Sql({"/data/datasetData.sql", "/data/gigData.sql",
        "/data/executionJobData.sql", "/data/executionResultData.sql"})
    public void getAllExecutionResults_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<List<ExecutionResultDTO>> response = restTemplate.exchange(
            "/api/executionResults", HttpMethod.GET, request, new ParameterizedTypeReference<>() {
            });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals( 1000L, response.getBody().get(0).getId());
    }

    @Test
    @Sql({"/data/datasetData.sql", "/data/gigData.sql",
        "/data/executionJobData.sql", "/data/executionResultData.sql"})
    public void getExecutionResult_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ExecutionResultDTO> response = restTemplate.exchange(
            "/api/executionResults/1000", HttpMethod.GET, request, ExecutionResultDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Aenean pulvinar...", response.getBody().getLocation());
    }

    @Test
    public void getExecutionResult_notFound() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/executionResults/1866", HttpMethod.GET, request, ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ResponseStatusException", response.getBody().getException());
    }

    @Test
    @Sql({"/data/datasetData.sql", "/data/gigData.sql", "/data/executionJobData.sql"})
    public void createExecutionResult_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/executionResultDTORequest.json"), headers());
        final ResponseEntity<Long> response = restTemplate.exchange(
            "/api/executionResults", HttpMethod.POST, request, Long.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, executionResultRepository.count());
    }

    @Test
    public void createExecutionResult_missingField() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/executionResultDTORequest_missingField.json"), headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/executionResults", HttpMethod.POST, request, ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("MethodArgumentNotValidException", response.getBody().getException());
        assertEquals("location", response.getBody().getFieldErrors().get(0).getField());
    }

    @Test
    @Sql({"/data/datasetData.sql", "/data/gigData.sql", "/data/executionJobData.sql", "/data/executionResultData.sql"})
    public void updateExecutionResult_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/executionResultDTORequest.json"), headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/executionResults/1000", HttpMethod.PUT, request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ut pellentesque sapien...",
            executionResultRepository.findById(1000L).get().getLocation());
    }

    @Test
    @Sql({"/data/datasetData.sql", "/data/gigData.sql",
        "/data/executionJobData.sql", "/data/executionResultData.sql"})
    public void deleteExecutionResult_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/executionResults/1000", HttpMethod.DELETE, request, Void.class);

        assertAll("Evaluate state of db after delete",
            () -> assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()),
            () -> assertEquals(0, executionResultRepository.count()),
            () -> assertEquals(1, executionJobRepository.count()),
            () -> assertEquals(1, gigRepository.count())
        );
    }

    @Test
    @Sql({"/data/datasetData.sql", "/data/gigData.sql", "/data/executionJobData.sql", "/data/executionResultData.sql"})
    public void patchUpdateExecutionResult_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/executionResultPatchDTORequest.json"), headers());
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/executionResults/1000", HttpMethod.PATCH, request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ut pellentesque sapien...test",
            executionResultRepository.findById(1000L).get().getLocation());
        assertEquals("Donec ac nibh...test",
            executionResultRepository.findById(1000L).get().getStorageType());
    }
}
