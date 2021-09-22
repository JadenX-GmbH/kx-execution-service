package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.config.BaseIT;
import com.jadenx.kxexecutionservice.model.PaginatedResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest extends BaseIT {

    // CHECKSTYLE IGNORE check FOR NEXT 1 LINES
    private static final String JWT_TOKEN = "eyJraWQiOiJvUVA3Zlp4NXN2SHRZVVptS0ZPeUw2WXVIUkFWdVZCclZ0XC80QkdaVHVhTT0iLCJhbGciOiJSUzI1NiJ9.eyJhdF9oYXNoIjoiUzdzQkNBY28wd25sRzdBVkl5YmI3QSIsInN1YiI6IjhiOWVlNjBjLTYxMDItNDYwMS04NTMwLTA0MWMwMWY0YTZlOSIsImF1ZCI6IjY0bDFrMzJrajc5OXIyczJ0NGM5c21hdmE1IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNjI3ODk2MzM3LCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtY2VudHJhbC0xLmFtYXpvbmF3cy5jb21cL2V1LWNlbnRyYWwtMV9mZDVENG9kbkEiLCJjb2duaXRvOnVzZXJuYW1lIjoiOGI5ZWU2MGMtNjEwMi00NjAxLTg1MzAtMDQxYzAxZjRhNmU5IiwiZXhwIjoxNjI3OTgyNzM3LCJpYXQiOjE2Mjc4OTYzMzcsImp0aSI6IjkwNmU0YjQ0LWZiNTctNDU1ZC1iNzgzLTEzOTI1MzY2MDVmZiIsImVtYWlsIjoiamFjZWsuamFuY3p1cmFAamFkZW54LmNvbSJ9.f3PhccH8P6fevh0U9sgzFlSTAeig1gDPps99e5y4RQF07dVEsNDh2JA5bNEHVtzrGmUycNsYMiE8qzQGyn-V38gbi7eTyTlfaQRjr8-GcghFL1j43DtNUW-Hxy84qjHrC_eFJ1mdfC_BZ19zmB2bU8OQjm6SQ3IT-gIHOKivtHFWq7unweRXz1YpZHZeQqzRGWO_k5M1eM8umLA-xWD2JaZgL4Mm4IYxOEXa_n_x7LN2p2J8JpIWiuXjBd44ANXGdeloVQJAsxQDfsevljH7aPbMyaL7E0UGRnIb7CJISDUPDlheR0ye0mNcisy4YnT-jPe9yw65DuEXqi4h2WHZxg";


    @Test
    @Sql({"/data/datasetData.sql", "/data/gigData.sql" })
    public void getDataSetsByUser_success() {
        var headers = headers();
        headers.setBearerAuth(JWT_TOKEN);
        final HttpEntity<String> request = new HttpEntity<>(null,headers);
        final ResponseEntity<PaginatedResponse<?>> response = restTemplate.exchange(
            "/api/users/datasets?page=0&size=1", HttpMethod.GET,
            request, new ParameterizedTypeReference<PaginatedResponse<?>>(){

            });
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals( 1, response.getBody().getData().size());

    }


}
