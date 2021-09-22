package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.ExecutionResult;
import com.jadenx.kxexecutionservice.mapper.ExecutionResultMapper;
import com.jadenx.kxexecutionservice.mapper.ExecutionResultPatchMapper;
import com.jadenx.kxexecutionservice.model.ExecutionResultDTO;
import com.jadenx.kxexecutionservice.model.ExecutionResultPatchDTO;
import com.jadenx.kxexecutionservice.repos.ExecutionResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ExecutionResultServiceImpl implements ExecutionResultService {

    private final ExecutionResultRepository executionResultRepository;
    private final ExecutionResultMapper executionResultMapper;
    private final ExecutionResultPatchMapper executionResultPatchMapper;

    @Override
    public List<ExecutionResultDTO> findAll() {
        return executionResultRepository.findAll()
            .stream()
            .map(executionResult -> executionResultMapper.mapToDTO(executionResult, new ExecutionResultDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public ExecutionResultDTO get(final Long id) {
        return executionResultRepository.findById(id)
            .map(executionResult -> executionResultMapper.mapToDTO(executionResult, new ExecutionResultDTO()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Long create(final ExecutionResultDTO executionResultDTO) {
        final ExecutionResult executionResult = new ExecutionResult();
        executionResultMapper.mapToEntity(executionResultDTO, executionResult);
        return executionResultRepository.save(executionResult).getId();
    }

    @Override
    public void update(final Long id, final ExecutionResultDTO executionResultDTO) {
        final ExecutionResult executionResult = executionResultRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        executionResultMapper.mapToEntity(executionResultDTO, executionResult);
        executionResultRepository.save(executionResult);
    }

    @Override
    public void delete(final Long id) {
        executionResultRepository.deleteById(id);
    }

    @Override
    public void patchUpdate(final Long id, final ExecutionResultPatchDTO executionResultPatchDTO) {
        final ExecutionResult executionResult = executionResultRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        executionResultPatchMapper.mapPatchDTOToEntity(executionResultPatchDTO, executionResult);
        executionResultRepository.save(executionResult);
    }

}
