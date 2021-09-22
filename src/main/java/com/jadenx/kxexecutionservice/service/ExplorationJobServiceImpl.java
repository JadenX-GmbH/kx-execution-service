package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.ExplorationJob;
import com.jadenx.kxexecutionservice.mapper.ExplorationJobMapper;
import com.jadenx.kxexecutionservice.model.ExplorationJobDTO;
import com.jadenx.kxexecutionservice.repos.ExplorationJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ExplorationJobServiceImpl implements ExplorationJobService {

    private final ExplorationJobRepository explorationJobRepository;
    private final ExplorationJobMapper explorationJobMapper;

    @Override
    public List<ExplorationJobDTO> findAll() {
        return explorationJobRepository.findAll()
            .stream()
            .map(explorationJob -> explorationJobMapper.mapToDTO(explorationJob, new ExplorationJobDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public ExplorationJobDTO get(final Long id) {
        return explorationJobRepository.findById(id)
            .map(explorationJob -> explorationJobMapper.mapToDTO(explorationJob, new ExplorationJobDTO()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Long create(final ExplorationJobDTO explorationJobDTO) {
        final ExplorationJob explorationJob = new ExplorationJob();
        explorationJobMapper.mapToEntity(explorationJobDTO, explorationJob);
        return explorationJobRepository.save(explorationJob).getId();
    }

    @Override
    public void update(final Long id, final ExplorationJobDTO explorationJobDTO) {
        final ExplorationJob explorationJob = explorationJobRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        explorationJobMapper.mapToEntity(explorationJobDTO, explorationJob);
        explorationJobRepository.save(explorationJob);
    }

    @Override
    public void delete(final Long id) {
        explorationJobRepository.deleteById(id);
    }

}
