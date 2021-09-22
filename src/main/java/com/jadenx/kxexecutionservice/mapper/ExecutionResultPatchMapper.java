package com.jadenx.kxexecutionservice.mapper;

import com.jadenx.kxexecutionservice.domain.ExecutionJob;
import com.jadenx.kxexecutionservice.domain.ExecutionResult;
import com.jadenx.kxexecutionservice.model.ExecutionResultPatchDTO;
import com.jadenx.kxexecutionservice.repos.ExecutionJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class ExecutionResultPatchMapper {

    private final ExecutionJobRepository executionJobRepository;

    public ExecutionResult mapPatchDTOToEntity(final ExecutionResultPatchDTO executionResultPatchDTO,
                                        final ExecutionResult executionResult) {
        executionResult.setLocation(executionResultPatchDTO.getLocation() == null
            ? executionResult.getLocation() : executionResultPatchDTO.getLocation());
        executionResult.setStorageType(executionResultPatchDTO.getStorageType() == null
            ? executionResult.getStorageType() : executionResultPatchDTO.getStorageType());
        executionResult.setTransactionId(executionResultPatchDTO.getTransactionId() == null
            ? executionResult.getTransactionId() : executionResultPatchDTO.getTransactionId());
        executionResult.setBlockchainIdentifier(executionResultPatchDTO.getBlockchainIdentifier() == null
            ? executionResult.getBlockchainIdentifier() : executionResultPatchDTO.getBlockchainIdentifier());
        if (executionResultPatchDTO.getExecutionJob() != null
            && (executionResult.getExecutionJob() == null
            || !executionResult.getExecutionJob().getId().equals(executionResultPatchDTO.getExecutionJob()))) {
            final ExecutionJob executionJob = executionJobRepository.findById(executionResultPatchDTO.getExecutionJob())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "executionJob not found"));
            executionResult.setExecutionJob(executionJob);
        }
        return executionResult;
    }
}
