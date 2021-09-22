package com.jadenx.kxexecutionservice.mapper;

import com.jadenx.kxexecutionservice.domain.ExecutionJob;
import com.jadenx.kxexecutionservice.domain.ExecutionResult;
import com.jadenx.kxexecutionservice.model.ExecutionResultDTO;
import com.jadenx.kxexecutionservice.repos.ExecutionJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class ExecutionResultMapper {

    private final ExecutionJobRepository executionJobRepository;

    public ExecutionResultDTO mapToDTO(final ExecutionResult executionResult,
                                       final ExecutionResultDTO executionResultDTO) {
        executionResultDTO.setId(executionResult.getId());
        executionResultDTO.setLocation(executionResult.getLocation());
        executionResultDTO.setStorageType(executionResult.getStorageType());
        executionResultDTO.setTransactionId(executionResult.getTransactionId());
        executionResultDTO.setBlockchainIdentifier(executionResult.getBlockchainIdentifier());
        executionResultDTO.setExecutionJob(
            executionResult.getExecutionJob() == null ? null : executionResult.getExecutionJob().getId());
        return executionResultDTO;
    }

    public ExecutionResult mapToEntity(final ExecutionResultDTO executionResultDTO,
                                       final ExecutionResult executionResult) {
        executionResult.setLocation(executionResultDTO.getLocation());
        executionResult.setStorageType(executionResultDTO.getStorageType());
        executionResult.setTransactionId(executionResultDTO.getTransactionId());
        executionResult.setBlockchainIdentifier(executionResultDTO.getBlockchainIdentifier());
        if ((executionResult.getExecutionJob() == null
            || ((executionResult.getExecutionJob() != null)
            && !executionResult.getExecutionJob().getId().equals(executionResultDTO.getExecutionJob())))) {
            final ExecutionJob executionJob = executionJobRepository.findById(executionResultDTO.getExecutionJob())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "executionJob not found"));
            executionResult.setExecutionJob(executionJob);
        }
        return executionResult;
    }
}
