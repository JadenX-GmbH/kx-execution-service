package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.config.BaseIT;
import com.jadenx.kxexecutionservice.model.DatasetDTO;
import com.jadenx.kxexecutionservice.model.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DatasetControllerTest extends BaseIT {

    @Test
    @Sql("/data/datasetData.sql")
    public void getAllDatasets_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<List<DatasetDTO>> response = restTemplate.exchange(
            "/api/datasets", HttpMethod.GET, request, new ParameterizedTypeReference<List<DatasetDTO>>() {
            });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals((long) 1500, response.getBody().get(0).getId());
    }

    @Test
    @Sql("/data/datasetData.sql")
    public void getDataset_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<DatasetDTO> response = restTemplate.exchange(
            "/api/datasets/1500", HttpMethod.GET, request, DatasetDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Donec ac nibh...", response.getBody().getTitle());
    }

    @Test
    public void getDataset_notFound() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/datasets/2166", HttpMethod.GET, request, ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ResponseStatusException", response.getBody().getException());
    }

    @Test
    public void createDataset_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/datasetDTORequest.json"), headers());
        final ResponseEntity<Long> response = restTemplate.exchange(
            "/api/datasets", HttpMethod.POST, request, Long.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, datasetRepository.count());
    }

    @Test
    public void createDataset_missingField() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/datasetDTORequest_missingField.json"), headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/datasets", HttpMethod.POST, request, ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("MethodArgumentNotValidException", response.getBody().getException());
        assertEquals("hash", response.getBody().getFieldErrors().get(0).getField());
    }

    @Test
    @Sql("/data/datasetData.sql")
    public void updateDataset_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/datasetDTORequest.json"), headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/datasets/1500", HttpMethod.PUT, request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Aenean pulvinar...", datasetRepository.findById(1500L).get().getTitle());
    }

    @Test
    @Sql("/data/datasetData.sql")
    public void deleteDataset_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/datasets/1500", HttpMethod.DELETE, request, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, datasetRepository.count());
    }

}
