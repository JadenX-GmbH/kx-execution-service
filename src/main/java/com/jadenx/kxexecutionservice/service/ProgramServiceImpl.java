package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.Program;
import com.jadenx.kxexecutionservice.mapper.ProgramMapper;
import com.jadenx.kxexecutionservice.mapper.ProgramPatchMapper;
import com.jadenx.kxexecutionservice.model.ProgramDTO;
import com.jadenx.kxexecutionservice.model.ProgramPatchDTO;
import com.jadenx.kxexecutionservice.repos.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;
    private final ProgramPatchMapper programPatchMapper;

    @Override
    public List<ProgramDTO> findAll() {
        return programRepository.findAll()
            .stream()
            .map(program -> programMapper.mapToDTO(program, new ProgramDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public ProgramDTO get(final Long id) {
        return programRepository.findById(id)
            .map(program -> programMapper.mapToDTO(program, new ProgramDTO()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Long create(final ProgramDTO programDTO) {
        final Program program = new Program();
        programMapper.mapToEntity(programDTO, program);
        return programRepository.save(program).getId();
    }

    @Override
    public void update(final Long id, final ProgramDTO programDTO) {
        final Program program = programRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        programMapper.mapToEntity(programDTO, program);
        programRepository.save(program);
    }

    @Override
    public void delete(final Long id) {
        programRepository.deleteById(id);
    }

    @Override
    public void patchUpdate(final Long id, final ProgramPatchDTO programPatchDTO) {
        final Program program = programRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        programPatchMapper.mapPatchDTOToEntity(programPatchDTO, program);
        programRepository.save(program);
    }

}
