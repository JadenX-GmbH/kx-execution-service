package com.jadenx.kxexecutionservice.mapper;

import com.jadenx.kxexecutionservice.domain.Dataset;
import com.jadenx.kxexecutionservice.domain.ExplorationJob;
import com.jadenx.kxexecutionservice.domain.Gig;
import com.jadenx.kxexecutionservice.model.ExplorationJobDTO;
import com.jadenx.kxexecutionservice.model.ExplorationResultDTO;
import com.jadenx.kxexecutionservice.repos.DatasetRepository;
import com.jadenx.kxexecutionservice.repos.GigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class ExplorationJobMapper {

    private final GigRepository gigRepository;
    private final ExplorationResultMapper explorationResultMapper;
    private final DatasetRepository datasetRepository;

    public ExplorationJobDTO mapToDTO(final ExplorationJob explorationJob,
                                       final ExplorationJobDTO explorationJobDTO) {
        final ExplorationResultDTO explorationResultDTO = new ExplorationResultDTO();
        explorationJobDTO.setId(explorationJob.getId());
        explorationJobDTO.setDescription(explorationJob.getDescription());
        explorationJobDTO.setCodeHash(explorationJob.getCodeHash());
        explorationJobDTO.setGig(explorationJob.getGig() == null ? null : explorationJob.getGig().getId());
        explorationJobDTO.setExplorationResultDTO(explorationJob.getExplorationResult() == null
            ? null : explorationResultMapper.mapToDTO(explorationJob.getExplorationResult(), explorationResultDTO));
        explorationJobDTO.setDataset(explorationJob.getDataset() == null ? null : explorationJob.getDataset().getId());
        return explorationJobDTO;
    }

    public ExplorationJob mapToEntity(final ExplorationJobDTO explorationJobDTO,
                                       final ExplorationJob explorationJob) {
        explorationJob.setDescription(explorationJobDTO.getDescription());
        explorationJob.setCodeHash(explorationJobDTO.getCodeHash());
        if (explorationJobDTO.getGig() != null
            && (explorationJob.getGig() == null
            || !explorationJob.getGig().getId().equals(explorationJobDTO.getGig()))) {
            final Gig gig = gigRepository.findById(explorationJobDTO.getGig())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "gig not found"));
            explorationJob.setGig(gig);
        }
        if (explorationJobDTO.getDataset() != null
            && (explorationJob.getDataset() == null
            || !explorationJob.getDataset().getId().equals(explorationJobDTO.getDataset()))) {
            final Dataset dataset = datasetRepository.findById(explorationJobDTO.getDataset())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dataset not found"));
            explorationJob.setDataset(dataset);
        }
        return explorationJob;
    }

}
