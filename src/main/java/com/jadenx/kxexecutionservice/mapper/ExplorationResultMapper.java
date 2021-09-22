package com.jadenx.kxexecutionservice.mapper;

import com.jadenx.kxexecutionservice.domain.ExplorationJob;
import com.jadenx.kxexecutionservice.domain.ExplorationResult;
import com.jadenx.kxexecutionservice.model.ExplorationResultDTO;
import com.jadenx.kxexecutionservice.repos.ExplorationJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class ExplorationResultMapper {

    private final ExplorationJobRepository explorationJobRepository;

    public ExplorationResultDTO mapToDTO(final ExplorationResult explorationResult,
                                          final ExplorationResultDTO explorationResultDTO) {
        explorationResultDTO.setId(explorationResult.getId());
        explorationResultDTO.setLocation(explorationResult.getLocation());
        explorationResultDTO.setStorateType(explorationResult.getStorateType());
        explorationResultDTO.setExplorationJob(
            explorationResult.getExplorationJob() == null ? null : explorationResult.getExplorationJob().getId());
        return explorationResultDTO;
    }

    public ExplorationResult mapToEntity(final ExplorationResultDTO explorationResultDTO,
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
