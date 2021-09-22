package com.jadenx.kxexecutionservice.mapper;

import com.jadenx.kxexecutionservice.domain.*;
import com.jadenx.kxexecutionservice.model.ExecutionJobDTO;
import com.jadenx.kxexecutionservice.model.ExecutionResultDTO;
import com.jadenx.kxexecutionservice.model.ProgramDTO;
import com.jadenx.kxexecutionservice.repos.DatasetRepository;
import com.jadenx.kxexecutionservice.repos.GigRepository;
import com.jadenx.kxexecutionservice.repos.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExecutionJobMapper {

    private final GigRepository gigRepository;
    private final DatasetRepository datasetRepository;
    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;
    private final ExecutionResultMapper executionResultMapper;

    public ExecutionJobDTO mapToDTO(final ExecutionJob executionJob,
                                    final ExecutionJobDTO executionJobDTO) {
        final ExecutionResultDTO executionResultDTO = new ExecutionResultDTO();
        executionJobDTO.setId(executionJob.getId());
        executionJobDTO.setPriceToken(executionJob.getPriceToken());
        executionJobDTO.setDescription(executionJob.getDescription());
        executionJobDTO.setExecutionType(executionJob.getExecutionType());
        executionJobDTO.setWorkerpool(executionJob.getWorkerpool());
        executionJobDTO.setWorker(executionJob.getWorker());
        executionJobDTO.setDealId(executionJob.getDealId());
        executionJobDTO.setDealBlockchainIdentifier(executionJob.getDealBlockchainIdentifier());
        executionJobDTO.setTaskId(executionJob.getTaskId());
        executionJobDTO.setTaskBlockchainIdentifier(executionJob.getTaskBlockchainIdentifier());
        executionJobDTO.setCategory(executionJob.getCategory());
        executionJobDTO.setTrust(executionJob.getTrust());
        executionJobDTO.setGig(executionJob.getGig() == null ? null : executionJob.getGig().getId());
        executionJobDTO.setExecutionResultDTO(executionJob.getExecutionResult() == null ? null :
            executionResultMapper.mapToDTO(executionJob.getExecutionResult(),executionResultDTO));
        executionJobDTO.setProgramDTOList(executionJob.getExecutionJobPrograms().stream()
            .map(program -> programMapper.mapToDTO(program, new ProgramDTO()))
            .collect(Collectors.toList()));
        executionJobDTO.setDataset(executionJob.getDataset() == null ? null : executionJob.getDataset().getId());
        executionJobDTO.setInputParameters(executionJob.getInputParameters() == null ? null :
            executionJob.getInputParameters().stream()
                .map(ExecutionInputParameter::getInputParameter)
                .collect(Collectors.toList()));
        return executionJobDTO;
    }

    public ExecutionJob mapToEntity(final ExecutionJobDTO executionJobDTO,
                                    final ExecutionJob executionJob) {
        executionJob.setPriceToken(executionJobDTO.getPriceToken());
        executionJob.setDescription(executionJobDTO.getDescription());
        executionJob.setExecutionType(executionJobDTO.getExecutionType());
        executionJob.setWorkerpool(executionJobDTO.getWorkerpool());
        executionJob.setWorker(executionJobDTO.getWorker());
        executionJob.setDealId(executionJobDTO.getDealId());
        executionJob.setDealBlockchainIdentifier(executionJobDTO.getDealBlockchainIdentifier());
        executionJob.setTaskId(executionJobDTO.getTaskId());
        executionJob.setTaskBlockchainIdentifier(executionJobDTO.getTaskBlockchainIdentifier());
        executionJob.setCategory(executionJobDTO.getCategory());
        executionJob.setTrust(executionJobDTO.getTrust());
        if (executionJobDTO.getGig() != null
            && (executionJob.getGig() == null
            || !executionJob.getGig().getId().equals(executionJobDTO.getGig()))) {
            final Gig gig = gigRepository.findById(executionJobDTO.getGig())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "gig not found"));
            executionJob.setGig(gig);
        }
        if (executionJobDTO.getDataset() != null
            && (executionJob.getDataset() == null
            || !executionJob.getDataset().getId().equals(executionJobDTO.getDataset()))) {
            final Dataset dataset = datasetRepository.findById(executionJobDTO.getDataset())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dataset not found"));
            executionJob.setDataset(dataset);
        }
        if (executionJobDTO.getInputParameters() != null) {
            executionJobDTO.getInputParameters().forEach(inputParameters -> {
                ExecutionInputParameter executionInputParameters = new ExecutionInputParameter();
                executionInputParameters.setInputParameter(inputParameters);
                executionInputParameters.setExecutionJob(executionJob);
                executionJob.getInputParameters().add(executionInputParameters);
            });
        }
        if (executionJobDTO.getProgramDTOList() != null) {
            Set<Program> programSet = new HashSet<>();
            executionJobDTO.getProgramDTOList().forEach(programDTO -> {
                Optional<Program> existingProgram = Optional.empty();
                if (programDTO.getId() != null) {
                    existingProgram = programRepository.findById(programDTO.getId());
                }
                Program program = new Program();
                programMapper.mapToEntity(programDTO, program);
                if (!existingProgram.isEmpty()) {
                    program.setId(programDTO.getId());
                } else {
                    program.setExecutionJob(executionJob);
                }
                programSet.add(program);
            });
            executionJob.setExecutionJobPrograms(programSet);
        }
        return executionJob;
    }
}
