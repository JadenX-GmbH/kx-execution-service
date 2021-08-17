package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.config.BaseIT;
import com.jadenx.kxexecutionservice.model.ErrorResponse;
import com.jadenx.kxexecutionservice.model.ExplorationJobDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ExplorationJobControllerTest extends BaseIT {

    @Test
    @Sql({"/data/gigData.sql", "/data/explorationJobData.sql"})
    public void getAllExplorationJobs_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<List<ExplorationJobDTO>> response = restTemplate.exchange(
            "/api/explorationJobs", HttpMethod.GET, request,
            new ParameterizedTypeReference<List<ExplorationJobDTO>>() {
            });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals((long) 1100, response.getBody().get(0).getId());
    }

    @Test
    @Sql({"/data/gigData.sql", "/data/explorationJobData.sql", "/data/explorationResultData.sql"})
    public void getExplorationJob_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ExplorationJobDTO> response = restTemplate.exchange(
            "/api/explorationJobs/1100", HttpMethod.GET, request, ExplorationJobDTO.class);

        assertAll(
            ()-> assertEquals(HttpStatus.OK, response.getStatusCode()),
            ()-> assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit,"
                +  " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                response.getBody().getDescription()),
            ()-> assertNotNull(response.getBody().getExplorationResultDTO()),
            ()-> assertEquals(1300, response.getBody().getExplorationResultDTO().getId())
        );
    }

    @Test
    public void getExplorationJob_notFound() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/explorationJobs/1766", HttpMethod.GET, request, ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ResponseStatusException", response.getBody().getException());
    }

    @Test
    @Sql("/data/gigData.sql")
    public void createExplorationJob_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/explorationJobDTORequest.json"), headers());
        final ResponseEntity<Long> response = restTemplate.exchange(
            "/api/explorationJobs", HttpMethod.POST, request, Long.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, explorationJobRepository.count());
    }

    @Test
    @Sql({"/data/gigData.sql", "/data/explorationJobData.sql"})
    public void updateExplorationJob_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/explorationJobDTORequest.json"), headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/explorationJobs/1100", HttpMethod.PUT, request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque "
                + "laudantium, totam rem aperiam.",
            explorationJobRepository.findById(1100L).get().getDescription());
    }

    @Test
    @Sql({"/data/gigData.sql", "/data/explorationJobData.sql", "/data/explorationResultData.sql"})
    public void deleteExplorationJob_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/explorationJobs/1100", HttpMethod.DELETE, request, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, explorationJobRepository.count());
        assertEquals(0, explorationJobRepository.count());
        assertEquals(0, explorationResultRepository.count());
    }

}
