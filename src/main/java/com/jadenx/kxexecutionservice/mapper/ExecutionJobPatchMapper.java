package com.jadenx.kxexecutionservice.mapper;

import com.jadenx.kxexecutionservice.domain.*;
import com.jadenx.kxexecutionservice.model.ExecutionJobPatchDTO;
import com.jadenx.kxexecutionservice.repos.DatasetRepository;
import com.jadenx.kxexecutionservice.repos.GigRepository;
import com.jadenx.kxexecutionservice.repos.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ExecutionJobPatchMapper {

    private final GigRepository gigRepository;
    private final DatasetRepository datasetRepository;
    private final ProgramRepository programRepository;
    private final ProgramPatchMapper programPatchMapper;

    public ExecutionJob mapPatchDTOToEntity(final ExecutionJobPatchDTO executionJobPatchDTO,
                                    final ExecutionJob executionJob) {
        executionJob.setPriceToken(executionJobPatchDTO.getPriceToken() == null
            ? executionJob.getPriceToken() : executionJobPatchDTO.getPriceToken());
        executionJob.setDescription(executionJobPatchDTO.getDescription() == null
            ? executionJob.getDescription() : executionJobPatchDTO.getDescription());
        executionJob.setExecutionType(executionJobPatchDTO.getExecutionType() == null
            ? executionJob.getExecutionType() : executionJobPatchDTO.getExecutionType());
        executionJob.setWorkerpool(executionJobPatchDTO.getWorkerpool() == null
            ? executionJob.getWorkerpool() : executionJobPatchDTO.getWorkerpool());
        executionJob.setWorker(executionJobPatchDTO.getWorker() == null
            ? executionJob.getWorker() : executionJobPatchDTO.getWorker());
        executionJob.setDealId(executionJobPatchDTO.getDealId() == null
            ? executionJob.getDealId() : executionJobPatchDTO.getDealId());
        executionJob.setDealBlockchainIdentifier(executionJobPatchDTO.getDealBlockchainIdentifier() == null
            ? executionJob.getDealBlockchainIdentifier() : executionJobPatchDTO.getDealBlockchainIdentifier());
        executionJob.setTaskId(executionJobPatchDTO.getTaskId() == null
            ? executionJob.getTaskId() : executionJobPatchDTO.getTaskId());
        executionJob.setTaskBlockchainIdentifier(executionJobPatchDTO.getTaskBlockchainIdentifier() == null
            ? executionJob.getTaskBlockchainIdentifier() : executionJobPatchDTO.getTaskBlockchainIdentifier());
        executionJob.setCategory(executionJobPatchDTO.getCategory());
        executionJob.setTrust(executionJobPatchDTO.getTrust());
        if (executionJobPatchDTO.getGig() != null
            && (executionJob.getGig() == null
            || !executionJob.getGig().getId().equals(executionJobPatchDTO.getGig()))) {
            final Gig gig = gigRepository.findById(executionJobPatchDTO.getGig())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "gig not found"));
            executionJob.setGig(gig);
        }
        if (executionJobPatchDTO.getDataset() != null
            && (executionJob.getDataset() == null
            || !executionJob.getDataset().getId().equals(executionJobPatchDTO.getDataset()))) {
            final Dataset dataset = datasetRepository.findById(executionJobPatchDTO.getDataset())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dataset not found"));
            executionJob.setDataset(dataset);
        }
        if (executionJobPatchDTO.getInputParameters() != null) {
            executionJobPatchDTO.getInputParameters().forEach(inputParameters -> {
                ExecutionInputParameter executionInputParameters = new ExecutionInputParameter();
                executionInputParameters.setInputParameter(inputParameters);
                executionInputParameters.setExecutionJob(executionJob);
                executionJob.getInputParameters().add(executionInputParameters);
            });
        }
        if (executionJobPatchDTO.getProgramPatchDTOList() != null) {
            Set<Program> programSet = new HashSet<>();
            executionJobPatchDTO.getProgramPatchDTOList().forEach(programPatchDTO -> {
                if (programPatchDTO.getId() != null) {
                    Program program = programRepository.findById(programPatchDTO.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
                    programPatchMapper.mapPatchDTOToEntity(programPatchDTO, program);
                    programSet.add(program);
                }
            });
            executionJob.setExecutionJobPrograms(programSet);
        }
        return executionJob;
    }
}
