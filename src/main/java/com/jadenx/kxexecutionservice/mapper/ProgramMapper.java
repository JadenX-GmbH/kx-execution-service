package com.jadenx.kxexecutionservice.mapper;

import com.jadenx.kxexecutionservice.domain.ExecutionJob;
import com.jadenx.kxexecutionservice.domain.Program;
import com.jadenx.kxexecutionservice.model.ProgramDTO;
import com.jadenx.kxexecutionservice.repos.ExecutionJobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ProgramMapper {

    private final ExecutionJobRepository executionJobRepository;

    public ProgramMapper(final ExecutionJobRepository executionJobRepository) {
        this.executionJobRepository = executionJobRepository;
    }

    public ProgramDTO mapToDTO(final Program program, final ProgramDTO programDTO) {
        programDTO.setId(program.getId());
        programDTO.setHash(program.getHash());
        programDTO.setLocation(program.getLocation());
        programDTO.setStorageType(program.getStorageType());
        programDTO.setExecutionJob(program.getExecutionJob() == null ? null : program.getExecutionJob().getId());
        programDTO.setBlockchainAddress(program.getBlockchainAddress());
        return programDTO;
    }

    public Program mapToEntity(final ProgramDTO programDTO, final Program program) {
        program.setHash(programDTO.getHash());
        program.setLocation(programDTO.getLocation());
        program.setStorageType(programDTO.getStorageType());
        if (programDTO.getExecutionJob() != null
            && (program.getExecutionJob() == null
            || !program.getExecutionJob().getId().equals(programDTO.getExecutionJob()))) {
            final ExecutionJob executionJob = executionJobRepository.findById(programDTO.getExecutionJob())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "executionJob not found"));
            program.setExecutionJob(executionJob);
            program.setBlockchainAddress(programDTO.getBlockchainAddress());
        }
        return program;
    }
}
