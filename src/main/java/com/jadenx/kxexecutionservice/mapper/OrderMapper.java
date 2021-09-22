package com.jadenx.kxexecutionservice.mapper;

import com.jadenx.kxexecutionservice.domain.ExecutionJob;
import com.jadenx.kxexecutionservice.domain.Order;
import com.jadenx.kxexecutionservice.model.OrderDTO;
import com.jadenx.kxexecutionservice.repos.ExecutionJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ExecutionJobRepository executionJobRepository;

    public OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setTransactionId(order.getTransactionId());
        orderDTO.setBlockchainIdentifier(order.getBlockchainIdentifier());
        orderDTO.setExecutionJob(order.getExecutionJob().getId());
        orderDTO.setName(order.getName());
        orderDTO.setOrderDetails(order.getOrderDetails());
        return orderDTO;
    }

    public Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setTransactionId(orderDTO.getTransactionId());
        order.setBlockchainIdentifier(orderDTO.getBlockchainIdentifier());
        if (orderDTO.getExecutionJob() != null
            && (order.getExecutionJob() == null
            || !order.getExecutionJob().getId().equals(orderDTO.getExecutionJob()))) {
            final ExecutionJob executionJob = executionJobRepository.findById(orderDTO.getExecutionJob())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "executionJob not found"));
            order.setExecutionJob(executionJob);
        }
        order.setName(orderDTO.getName());
        order.setOrderDetails(orderDTO.getOrderDetails());
        return order;
    }
}
