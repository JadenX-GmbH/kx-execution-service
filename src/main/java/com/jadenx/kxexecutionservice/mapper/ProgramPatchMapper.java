package com.jadenx.kxexecutionservice.mapper;

import com.jadenx.kxexecutionservice.domain.ExecutionJob;
import com.jadenx.kxexecutionservice.domain.Program;
import com.jadenx.kxexecutionservice.model.ProgramPatchDTO;
import com.jadenx.kxexecutionservice.repos.ExecutionJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class ProgramPatchMapper {

    private final ExecutionJobRepository executionJobRepository;
    public Program mapPatchDTOToEntity(final ProgramPatchDTO programPatchDTO, final Program program) {
        program.setHash(programPatchDTO.getHash() == null ? program.getHash() : programPatchDTO.getHash());
        program.setLocation(programPatchDTO.getLocation() == null ? program.getLocation() :
            programPatchDTO.getLocation());
        program.setStorageType(programPatchDTO.getStorageType() == null ? program.getStorageType() :
            programPatchDTO.getStorageType());
        if (programPatchDTO.getExecutionJob() != null
            && (program.getExecutionJob() == null
            || !program.getExecutionJob().getId().equals(programPatchDTO.getExecutionJob()))) {
            final ExecutionJob executionJob = executionJobRepository.findById(programPatchDTO.getExecutionJob())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "executionJob not found"));
            program.setExecutionJob(executionJob);
        }
        program.setBlockchainAddress(programPatchDTO.getBlockchainAddress() == null
            ? program.getBlockchainAddress() : programPatchDTO.getBlockchainAddress());
        return program;
    }

}
