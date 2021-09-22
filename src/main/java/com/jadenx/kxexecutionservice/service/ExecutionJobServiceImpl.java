package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.ExecutionJob;
import com.jadenx.kxexecutionservice.domain.Program;
import com.jadenx.kxexecutionservice.mapper.ExecutionJobMapper;
import com.jadenx.kxexecutionservice.mapper.ExecutionJobPatchMapper;
import com.jadenx.kxexecutionservice.model.*;
import com.jadenx.kxexecutionservice.proxy.FeignRestClientProxyJs;
import com.jadenx.kxexecutionservice.repos.ExecutionJobRepository;
import com.jadenx.kxexecutionservice.mapper.ProgramMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ExecutionJobServiceImpl implements ExecutionJobService {

    private final ExecutionJobRepository executionJobRepository;
    private final ProgramMapper programMapper;
    private final ExecutionJobMapper executionJobMapper;
    private final ExecutionJobPatchMapper executionJobPatchMapper;
    private final FeignRestClientProxyJs feignRestClientProxyJs;

    @Override
    public List<ExecutionJobDTO> findAll() {
        return executionJobRepository.findAll()
            .stream()
            .map(executionJob -> executionJobMapper.mapToDTO(executionJob, new ExecutionJobDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public ExecutionJobDTO get(final Long id) {
        return executionJobRepository.findById(id)
            .map(executionJob -> executionJobMapper.mapToDTO(executionJob, new ExecutionJobDTO()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Long create(final ExecutionJobDTO executionJobDTO) {
        final ExecutionJob executionJob = new ExecutionJob();
        executionJob.setInputParameters(new HashSet<>());
        executionJobMapper.mapToEntity(executionJobDTO, executionJob);
        ExecutionJob createdExecutionJob = executionJobRepository.save(executionJob);

        try {
            Optional<Program> program = createdExecutionJob.getExecutionJobPrograms()
                .stream().findFirst();
            if (!program.isEmpty()) {
                ResultBcRequestDTO resultBcRequestDTO = new ResultBcRequestDTO();
                resultBcRequestDTO.setMultiaddr(program.get().getLocation());
                resultBcRequestDTO.setChecksum(program.get().getHash());
                resultBcRequestDTO.setProgramId(program.get().getId());
                resultBcRequestDTO.setExecutionJobId(createdExecutionJob.getId());
                resultBcRequestDTO.setCategory(0);
                if (createdExecutionJob.getDataset() != null) {
                    List<String> datasetUrl = new ArrayList<>();
                    datasetUrl.add(createdExecutionJob.getDataset().getLocation());
                    resultBcRequestDTO.setInputParameters(datasetUrl);
                } else {
                    resultBcRequestDTO.setInputParameters(executionJobDTO.getInputParameters());
                }
                ResponseEntity<DealResponseDTO> response = feignRestClientProxyJs
                    .createExecutionJob(resultBcRequestDTO);
                log.info("executionJob successfully saved in blockchain");
                createdExecutionJob.setDealId(response.getBody().getDealId());
                executionJobRepository.save(createdExecutionJob);
            }
        } catch (Exception e) {
            log.error("Saving executionJob in blockchain failed!!");
        }
        return createdExecutionJob.getId();
    }

    @Override
    public void update(final Long id, final ExecutionJobDTO executionJobDTO) {
        final ExecutionJob executionJob = executionJobRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (executionJob.getInputParameters() == null ) {
            executionJob.setInputParameters(new HashSet<>());
        }
        executionJob.getInputParameters().clear();
        executionJobMapper.mapToEntity(executionJobDTO, executionJob);
        executionJobRepository.save(executionJob);
    }

    @Override
    public void delete(final Long id) {
        executionJobRepository.deleteById(id);
    }

    @Override
    public List<ProgramDTO> getProgramsByExecutionJob(final Long id) {
        return executionJobRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
            .getExecutionJobPrograms()
            .stream()
            .map(program -> programMapper.mapToDTO(program, new ProgramDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public void patchUpdate(final Long id, final ExecutionJobPatchDTO executionJobPatchDTO) {
        final ExecutionJob executionJob = executionJobRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (executionJobPatchDTO.getInputParameters() != null) {
            if (executionJob.getInputParameters() == null) {
                executionJob.setInputParameters(new HashSet<>());
            } else {
                executionJob.getInputParameters().clear();
            }
        }
        executionJobPatchMapper.mapPatchDTOToEntity(executionJobPatchDTO, executionJob);
        executionJobRepository.save(executionJob);
    }

}
