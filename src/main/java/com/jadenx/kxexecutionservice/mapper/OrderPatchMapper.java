package com.jadenx.kxexecutionservice.mapper;

import com.jadenx.kxexecutionservice.domain.ExecutionJob;
import com.jadenx.kxexecutionservice.domain.Order;
import com.jadenx.kxexecutionservice.model.OrderPatchDTO;
import com.jadenx.kxexecutionservice.repos.ExecutionJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class OrderPatchMapper {

    private final ExecutionJobRepository executionJobRepository;

    public Order mapPatchDTOToEntity(final OrderPatchDTO orderPatchDTO, final Order order) {
        order.setTransactionId(orderPatchDTO.getTransactionId() == null
            ? order.getTransactionId() : orderPatchDTO.getTransactionId());
        order.setBlockchainIdentifier(orderPatchDTO.getBlockchainIdentifier() == null
            ? order.getBlockchainIdentifier() : orderPatchDTO.getBlockchainIdentifier());
        if (orderPatchDTO.getExecutionJob() != null
            && (order.getExecutionJob() == null
            || !order.getExecutionJob().getId().equals(orderPatchDTO.getExecutionJob()))) {
            final ExecutionJob executionJob = executionJobRepository.findById(orderPatchDTO.getExecutionJob())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "executionJob not found"));
            order.setExecutionJob(executionJob);
        }
        order.setName(orderPatchDTO.getName() == null ? order.getName() : orderPatchDTO.getName());
        order.setOrderDetails(orderPatchDTO.getOrderDetails() == null
            ? order.getOrderDetails() : orderPatchDTO.getOrderDetails());
        return order;
    }
}
