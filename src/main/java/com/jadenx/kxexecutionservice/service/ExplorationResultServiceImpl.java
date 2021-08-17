package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.ExplorationJob;
import com.jadenx.kxexecutionservice.domain.ExplorationResult;
import com.jadenx.kxexecutionservice.model.ExplorationResultDTO;
import com.jadenx.kxexecutionservice.repos.ExplorationJobRepository;
import com.jadenx.kxexecutionservice.repos.ExplorationResultRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExplorationResultServiceImpl implements ExplorationResultService {

    private final ExplorationResultRepository explorationResultRepository;
    private final ExplorationJobRepository explorationJobRepository;

    public ExplorationResultServiceImpl(
        final ExplorationResultRepository explorationResultRepository,
        final ExplorationJobRepository explorationJobRepository) {
        this.explorationResultRepository = explorationResultRepository;
        this.explorationJobRepository = explorationJobRepository;
    }

    @Override
    public List<ExplorationResultDTO> findAll() {
        return explorationResultRepository.findAll()
            .stream()
            .map(explorationResult -> mapToDTO(explorationResult, new ExplorationResultDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public ExplorationResultDTO get(final Long id) {
        return explorationResultRepository.findById(id)
            .map(explorationResult -> mapToDTO(explorationResult, new ExplorationResultDTO()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Long create(final ExplorationResultDTO explorationResultDTO) {
        final ExplorationResult explorationResult = new ExplorationResult();
        mapToEntity(explorationResultDTO, explorationResult);
        return explorationResultRepository.save(explorationResult).getId();
    }

    @Override
    public void update(final Long id, final ExplorationResultDTO explorationResultDTO) {
        final ExplorationResult explorationResult = explorationResultRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(explorationResultDTO, explorationResult);
        explorationResultRepository.save(explorationResult);
    }

    @Override
    public void delete(final Long id) {
        explorationResultRepository.deleteById(id);
    }

    private ExplorationResultDTO mapToDTO(final ExplorationResult explorationResult,
                                          final ExplorationResultDTO explorationResultDTO) {
        explorationResultDTO.setId(explorationResult.getId());
        explorationResultDTO.setLocation(explorationResult.getLocation());
        explorationResultDTO.setStorateType(explorationResult.getStorateType());
        explorationResultDTO.setExplorationJob(
            explorationResult.getExplorationJob() == null ? null : explorationResult.getExplorationJob().getId());
        return explorationResultDTO;
    }

    private ExplorationResult mapToEntity(final ExplorationResultDTO explorationResultDTO,
                                          final ExplorationResult explorationResult) {
        explorationResult.setLocation(explorationResultDTO.getLocation());
        explorationResult.setStorateType(explorationResultDTO.getStorateType());
        if (explorationResultDTO.getExplorationJob() != null
            && (explorationResult.getExplorationJob() == null
            || !explorationResult.getExplorationJob().getId().equals(explorationResultDTO.getExplorationJob()))) {
            final ExplorationJob explorationJob = explorationJobRepository
                .findById(explorationResultDTO.getExplorationJob())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "explorationJob not found"));
            explorationResult.setExplorationJob(explorationJob);
        }
        return explorationResult;
    }

}
