package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.config.BaseIT;
import com.jadenx.kxexecutionservice.model.ErrorResponse;
import com.jadenx.kxexecutionservice.model.ExecutionJobDTO;
import com.jadenx.kxexecutionservice.model.ProgramDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ExecutionJobControllerTest extends BaseIT {

    @Test
    @Sql({"/data/datasetData.sql", "/data/gigData.sql", "/data/executionJobData.sql"})
    public void getAllExecutionJobs_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<List<ExecutionJobDTO>> response = restTemplate.exchange(
            "/api/executionJobs", HttpMethod.GET, request, new ParameterizedTypeReference<List<ExecutionJobDTO>>() {
            });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals( 1000L, response.getBody().get(0).getId());
    }

    @Test
    @Sql({"/data/datasetData.sql", "/data/gigData.sql", "/data/executionJobData.sql", "/data/executionResultData.sql"})
    public void getExecutionJob_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ExecutionJobDTO> response = restTemplate.exchange(
            "/api/executionJobs/1000", HttpMethod.GET, request, ExecutionJobDTO.class);

        assertAll(
            ()-> assertEquals(HttpStatus.OK, response.getStatusCode()),
            ()-> assertEquals(19.42d, response.getBody().getPriceToken()),
            ()-> assertNotNull(response.getBody().getExecutionResultDTO()),
            ()-> assertEquals(1000, response.getBody().getExecutionResultDTO().getId())
        );
    }

    @Test
    public void getExecutionJob_notFound() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/executionJobs/1666", HttpMethod.GET, request, ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ResponseStatusException", response.getBody().getException());
    }

    @Test
    @Sql({"/data/datasetData.sql", "/data/gigData.sql"})
    public void createExecutionJob_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/executionJobDTORequest.json"), headers());
        final ResponseEntity<Long> response = restTemplate.exchange(
            "/api/executionJobs", HttpMethod.POST, request, Long.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, executionJobRepository.count());
    }

    @Test
    public void createExecutionJob_missingField() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/executionJobDTORequest_missingField.json"), headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/executionJobs", HttpMethod.POST, request, ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("MethodArgumentNotValidException", response.getBody().getException());
        assertEquals("executionType", response.getBody().getFieldErrors().get(0).getField());
    }

    @Test
    @Sql({"/data/datasetData.sql", "/data/gigData.sql", "/data/executionJobData.sql", "/data/programData.sql"})
    public void updateExecutionJob_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/executionJobDTOUpdateRequest.json"), headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/executionJobs/1000", HttpMethod.PUT, request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(18.42d, executionJobRepository.findById(1000L).get().getPriceToken());
    }

    @Test
    @Sql({"/data/datasetData.sql", "/data/gigData.sql", "/data/executionJobData.sql", "/data/executionResultData.sql"})
    public void deleteExecutionJob_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/executionJobs/1000", HttpMethod.DELETE, request, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, executionJobRepository.count());
        assertEquals(0, executionResultRepository.count());
    }

    @Test
    @Sql({"/data/datasetData.sql", "/data/gigData.sql", "/data/executionJobData.sql", "/data/programData.sql"})
    public void getProgramsByExecutionJob() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<List<ProgramDTO>> response = restTemplate.exchange(
            "/api/executionJobs/1000/programs", HttpMethod.GET, request,
            new ParameterizedTypeReference<List<ProgramDTO>>() {});

        assertAll(
            ()-> assertEquals(HttpStatus.OK, response.getStatusCode()),
            ()-> assertEquals(1600, response.getBody().get(0).getId())
        );
    }

    @Test
    @Sql({"/data/datasetData.sql", "/data/gigData.sql", "/data/executionJobData.sql", "/data/programData.sql"})
    public void patchUpdateExecutionJob_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/executionJobPatchDTORequest.json"), headers());
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/executionJobs/1000", HttpMethod.PATCH, request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(20.42d, executionJobRepository.findById(1000L).get().getPriceToken());
    }
}
