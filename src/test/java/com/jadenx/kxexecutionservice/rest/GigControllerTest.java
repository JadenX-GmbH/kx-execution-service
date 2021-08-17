package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.config.BaseIT;
import com.jadenx.kxexecutionservice.model.ErrorResponse;
import com.jadenx.kxexecutionservice.model.GigDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class GigControllerTest extends BaseIT {

    // SUB: 8b9ee60c-6102-4601-8530-041c01f4a6e9
    // CHECKSTYLE IGNORE check FOR NEXT 1 LINES
    private static final String JWT_TOKEN = "eyJraWQiOiJvUVA3Zlp4NXN2SHRZVVptS0ZPeUw2WXVIUkFWdVZCclZ0XC80QkdaVHVhTT0iLCJhbGciOiJSUzI1NiJ9.eyJhdF9oYXNoIjoiUzdzQkNBY28wd25sRzdBVkl5YmI3QSIsInN1YiI6IjhiOWVlNjBjLTYxMDItNDYwMS04NTMwLTA0MWMwMWY0YTZlOSIsImF1ZCI6IjY0bDFrMzJrajc5OXIyczJ0NGM5c21hdmE1IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNjI3ODk2MzM3LCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtY2VudHJhbC0xLmFtYXpvbmF3cy5jb21cL2V1LWNlbnRyYWwtMV9mZDVENG9kbkEiLCJjb2duaXRvOnVzZXJuYW1lIjoiOGI5ZWU2MGMtNjEwMi00NjAxLTg1MzAtMDQxYzAxZjRhNmU5IiwiZXhwIjoxNjI3OTgyNzM3LCJpYXQiOjE2Mjc4OTYzMzcsImp0aSI6IjkwNmU0YjQ0LWZiNTctNDU1ZC1iNzgzLTEzOTI1MzY2MDVmZiIsImVtYWlsIjoiamFjZWsuamFuY3p1cmFAamFkZW54LmNvbSJ9.f3PhccH8P6fevh0U9sgzFlSTAeig1gDPps99e5y4RQF07dVEsNDh2JA5bNEHVtzrGmUycNsYMiE8qzQGyn-V38gbi7eTyTlfaQRjr8-GcghFL1j43DtNUW-Hxy84qjHrC_eFJ1mdfC_BZ19zmB2bU8OQjm6SQ3IT-gIHOKivtHFWq7unweRXz1YpZHZeQqzRGWO_k5M1eM8umLA-xWD2JaZgL4Mm4IYxOEXa_n_x7LN2p2J8JpIWiuXjBd44ANXGdeloVQJAsxQDfsevljH7aPbMyaL7E0UGRnIb7CJISDUPDlheR0ye0mNcisy4YnT-jPe9yw65DuEXqi4h2WHZxg";

    @Test
    @Sql("/data/gigData.sql")
    public void getAllGigs_success() {
        var headers = headers();
        headers.setBearerAuth(JWT_TOKEN);
        final HttpEntity<String> request = new HttpEntity<>(null, headers);
        final ResponseEntity<List<GigDTO>> response = restTemplate.exchange(
            "/api/gigs", HttpMethod.GET, request, new ParameterizedTypeReference<List<GigDTO>>() {
            });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1400L, response.getBody().get(0).getId());
    }

    @Test
    @Sql({"/data/gigData.sql", "/data/executionJobData.sql", "/data/programData.sql",
        "/data/executionResultData.sql"})
    public void getAllExecutionJobs_success() {
        var headers = headers();
        headers.setBearerAuth(JWT_TOKEN);
        final HttpEntity<String> request = new HttpEntity<>(null, headers);
        final ResponseEntity<List<GigDTO>> response = restTemplate.exchange(
            "/api/gigs/", HttpMethod.GET, request, new ParameterizedTypeReference<List<GigDTO>>() {
            });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1400L, response.getBody().get(0).getId());
    }

    @Test
    @Sql("/data/gigData.sql")
    public void getGig_success() {
        var headers = headers();
        headers.setBearerAuth(JWT_TOKEN);
        final HttpEntity<String> request = new HttpEntity<>(null, headers);
        final ResponseEntity<GigDTO> response = restTemplate.exchange(
            "/api/gigs/1400", HttpMethod.GET, request, GigDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(65L, response.getBody().getGigId());
    }

    @Test
    public void getGig_notFound() {
        var headers = headers();
        headers.setBearerAuth(JWT_TOKEN);
        final HttpEntity<String> request = new HttpEntity<>(null, headers);
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/gigs/2066", HttpMethod.GET, request, ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ResponseStatusException", response.getBody().getException());
    }

    @Test
    public void createGig_success() {
        var headers = headers();
        headers.setBearerAuth(JWT_TOKEN);
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/gigDTORequest.json"), headers);
        final ResponseEntity<Long> response = restTemplate.exchange(
            "/api/gigs", HttpMethod.POST, request, Long.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, gigRepository.count());
    }

    @Test
    public void createGig_missingField() {
        var headers = headers();
        headers.setBearerAuth(JWT_TOKEN);
        final HttpEntity<String> request = new HttpEntity<>(readResource(
            "/requests/gigDTORequest_missingField.json"), headers);
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/gigs", HttpMethod.POST, request, ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("MethodArgumentNotValidException", response.getBody().getException());
        assertEquals("gigId", response.getBody().getFieldErrors().get(0).getField());
    }

    @Test
    @Sql("/data/gigData.sql")
    public void updateGig_success() {
        final HttpEntity<String> request = new HttpEntity<>(readResource(
            "/requests/gigDTORequest.json"), headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/gigs/1400", HttpMethod.PUT, request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(66L, gigRepository.findById(1400L).get().getGigId());
    }

    @Test
    @Sql({"/data/gigData.sql", "/data/executionJobData.sql", "/data/programData.sql",
        "/data/executionResultData.sql"})
    public void deleteGig_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/gigs/1400", HttpMethod.DELETE, request, Void.class);

        assertAll("Evaluate state of db after delete",
            () -> assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()),
            () -> assertEquals(0, gigRepository.count()),
            () -> assertEquals(0, executionJobRepository.count()),
            () -> assertEquals(0, executionResultRepository.count()),
            () -> assertEquals(0, programRepository.count())

        );
    }

}
