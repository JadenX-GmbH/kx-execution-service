package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.ExplorationResult;
import com.jadenx.kxexecutionservice.mapper.ExplorationResultMapper;
import com.jadenx.kxexecutionservice.model.ExplorationResultDTO;
import com.jadenx.kxexecutionservice.repos.ExplorationResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class ExplorationResultServiceImpl implements ExplorationResultService {

    private final ExplorationResultRepository explorationResultRepository;
    private final ExplorationResultMapper explorationResultMapper;

    @Override
    public List<ExplorationResultDTO> findAll() {
        return explorationResultRepository.findAll()
            .stream()
            .map(explorationResult -> explorationResultMapper
                .mapToDTO(explorationResult, new ExplorationResultDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public ExplorationResultDTO get(final Long id) {
        return explorationResultRepository.findById(id)
            .map(explorationResult -> explorationResultMapper.mapToDTO(explorationResult, new ExplorationResultDTO()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Long create(final ExplorationResultDTO explorationResultDTO) {
        final ExplorationResult explorationResult = new ExplorationResult();
        explorationResultMapper.mapToEntity(explorationResultDTO, explorationResult);
        return explorationResultRepository.save(explorationResult).getId();
    }

    @Override
    public void update(final Long id, final ExplorationResultDTO explorationResultDTO) {
        final ExplorationResult explorationResult = explorationResultRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        explorationResultMapper.mapToEntity(explorationResultDTO, explorationResult);
        explorationResultRepository.save(explorationResult);
    }

    @Override
    public void delete(final Long id) {
        explorationResultRepository.deleteById(id);
    }

}
