package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.ExecutionJob;
import com.jadenx.kxexecutionservice.domain.Program;
import com.jadenx.kxexecutionservice.model.ProgramDTO;
import com.jadenx.kxexecutionservice.repos.ExecutionJobRepository;
import com.jadenx.kxexecutionservice.repos.ProgramRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final ExecutionJobRepository executionJobRepository;

    public ProgramServiceImpl(final ProgramRepository programRepository,
                              final ExecutionJobRepository executionJobRepository) {
        this.programRepository = programRepository;
        this.executionJobRepository = executionJobRepository;
    }

    @Override
    public List<ProgramDTO> findAll() {
        return programRepository.findAll()
            .stream()
            .map(program -> mapToDTO(program, new ProgramDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public ProgramDTO get(final Long id) {
        return programRepository.findById(id)
            .map(program -> mapToDTO(program, new ProgramDTO()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Long create(final ProgramDTO programDTO) {
        final Program program = new Program();
        mapToEntity(programDTO, program);
        return programRepository.save(program).getId();
    }

    @Override
    public void update(final Long id, final ProgramDTO programDTO) {
        final Program program = programRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(programDTO, program);
        programRepository.save(program);
    }

    @Override
    public void delete(final Long id) {
        programRepository.deleteById(id);
    }

    private ProgramDTO mapToDTO(final Program program, final ProgramDTO programDTO) {
        programDTO.setId(program.getId());
        programDTO.setHash(program.getHash());
        programDTO.setLocation(program.getLocation());
        programDTO.setStorageType(program.getStorageType());
        programDTO.setExecutionJob(program.getExecutionJob() == null ? null : program.getExecutionJob().getId());
        return programDTO;
    }

    private Program mapToEntity(final ProgramDTO programDTO, final Program program) {
        program.setHash(programDTO.getHash());
        program.setLocation(programDTO.getLocation());
        program.setStorageType(programDTO.getStorageType());
        if (programDTO.getExecutionJob() != null
            && (program.getExecutionJob() == null
            || !program.getExecutionJob().getId().equals(programDTO.getExecutionJob()))) {
            final ExecutionJob executionJob = executionJobRepository.findById(programDTO.getExecutionJob())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "executionJob not found"));
            program.setExecutionJob(executionJob);
        }
        return program;
    }

}
