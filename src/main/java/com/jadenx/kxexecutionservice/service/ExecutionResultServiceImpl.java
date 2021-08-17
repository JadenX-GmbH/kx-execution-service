package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.ExecutionJob;
import com.jadenx.kxexecutionservice.domain.ExecutionResult;
import com.jadenx.kxexecutionservice.model.ExecutionResultDTO;
import com.jadenx.kxexecutionservice.repos.ExecutionJobRepository;
import com.jadenx.kxexecutionservice.repos.ExecutionResultRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExecutionResultServiceImpl implements ExecutionResultService {

    private final ExecutionResultRepository executionResultRepository;
    private final ExecutionJobRepository executionJobRepository;

    public ExecutionResultServiceImpl(final ExecutionResultRepository executionResultRepository,
                                      final ExecutionJobRepository executionJobRepository) {
        this.executionResultRepository = executionResultRepository;
        this.executionJobRepository = executionJobRepository;
    }

    @Override
    public List<ExecutionResultDTO> findAll() {
        return executionResultRepository.findAll()
            .stream()
            .map(executionResult -> mapToDTO(executionResult, new ExecutionResultDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public ExecutionResultDTO get(final Long id) {
        return executionResultRepository.findById(id)
            .map(executionResult -> mapToDTO(executionResult, new ExecutionResultDTO()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Long create(final ExecutionResultDTO executionResultDTO) {
        final ExecutionResult executionResult = new ExecutionResult();
        mapToEntity(executionResultDTO, executionResult);
        return executionResultRepository.save(executionResult).getId();
    }

    @Override
    public void update(final Long id, final ExecutionResultDTO executionResultDTO) {
        final ExecutionResult executionResult = executionResultRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(executionResultDTO, executionResult);
        executionResultRepository.save(executionResult);
    }

    @Override
    public void delete(final Long id) {
        executionResultRepository.deleteById(id);
    }

    private ExecutionResultDTO mapToDTO(final ExecutionResult executionResult,
                                        final ExecutionResultDTO executionResultDTO) {
        executionResultDTO.setId(executionResult.getId());
        executionResultDTO.setLocation(executionResult.getLocation());
        executionResultDTO.setStorageType(executionResult.getStorageType());
        executionResultDTO.setTransactionId(executionResult.getTransactionId());
        executionResultDTO.setBlockchianIdentifier(executionResult.getBlockchianIdentifier());
        executionResultDTO.setExecutionJob(
            executionResult.getExecutionJob() == null ? null : executionResult.getExecutionJob().getId());
        return executionResultDTO;
    }

    private ExecutionResult mapToEntity(final ExecutionResultDTO executionResultDTO,
                                        final ExecutionResult executionResult) {
        executionResult.setLocation(executionResultDTO.getLocation());
        executionResult.setStorageType(executionResultDTO.getStorageType());
        executionResult.setTransactionId(executionResultDTO.getTransactionId());
        executionResult.setBlockchianIdentifier(executionResultDTO.getBlockchianIdentifier());
        if (executionResultDTO.getExecutionJob() != null
            && (executionResult.getExecutionJob() == null
            || !executionResult.getExecutionJob().getId().equals(executionResultDTO.getExecutionJob()))) {
            final ExecutionJob executionJob = executionJobRepository.findById(executionResultDTO.getExecutionJob())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "executionJob not found"));
            executionResult.setExecutionJob(executionJob);
        }
        return executionResult;
    }

}
