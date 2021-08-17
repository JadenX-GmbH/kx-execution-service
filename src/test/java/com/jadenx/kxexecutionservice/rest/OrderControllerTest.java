package com.jadenx.kxexecutionservice.rest;

import com.jadenx.kxexecutionservice.config.BaseIT;
import com.jadenx.kxexecutionservice.model.ErrorResponse;
import com.jadenx.kxexecutionservice.model.OrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OrderControllerTest extends BaseIT {

    @Test
    @Sql("/data/orderData.sql")
    public void getAllOrders_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<List<OrderDTO>> response = restTemplate.exchange(
            "/api/orders", HttpMethod.GET, request, new ParameterizedTypeReference<List<OrderDTO>>() {
            });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals((long) 1700, response.getBody().get(0).getId());
    }

    @Test
    @Sql("/data/orderData.sql")
    public void getOrder_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<OrderDTO> response = restTemplate.exchange(
            "/api/orders/1700", HttpMethod.GET, request, OrderDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Aenean pulvinar...", response.getBody().getTransactionId());
    }

    @Test
    public void getOrder_notFound() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/orders/2366", HttpMethod.GET, request, ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ResponseStatusException", response.getBody().getException());
    }

    @Test
    public void createOrder_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/orderDTORequest.json"), headers());
        final ResponseEntity<Long> response = restTemplate.exchange(
            "/api/orders", HttpMethod.POST, request, Long.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, orderRepository.count());
    }

    @Test
    public void createOrder_missingField() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/orderDTORequest_missingField.json"), headers());
        final ResponseEntity<ErrorResponse> response = restTemplate.exchange(
            "/api/orders", HttpMethod.POST, request, ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("MethodArgumentNotValidException", response.getBody().getException());
        assertEquals("transactionId", response.getBody().getFieldErrors().get(0).getField());
    }

    @Test
    @Sql("/data/orderData.sql")
    public void updateOrder_success() {
        final HttpEntity<String> request = new HttpEntity<>(
            readResource("/requests/orderDTORequest.json"), headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/orders/1700", HttpMethod.PUT, request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ut pellentesque sapien...", orderRepository.findById(1700L).get().getTransactionId());
    }

    @Test
    @Sql("/data/orderData.sql")
    public void deleteOrder_success() {
        final HttpEntity<String> request = new HttpEntity<>(null, headers());
        final ResponseEntity<Void> response = restTemplate.exchange(
            "/api/orders/1700", HttpMethod.DELETE, request, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, orderRepository.count());
    }

}
