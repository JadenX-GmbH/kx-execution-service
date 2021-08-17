package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.ExplorationJob;
import com.jadenx.kxexecutionservice.domain.ExplorationResult;
import com.jadenx.kxexecutionservice.domain.Gig;
import com.jadenx.kxexecutionservice.model.ExplorationJobDTO;
import com.jadenx.kxexecutionservice.model.ExplorationResultDTO;
import com.jadenx.kxexecutionservice.repos.ExplorationJobRepository;
import com.jadenx.kxexecutionservice.repos.GigRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExplorationJobServiceImpl implements ExplorationJobService {

    private final ExplorationJobRepository explorationJobRepository;
    private final GigRepository gigRepository;

    public ExplorationJobServiceImpl(final ExplorationJobRepository explorationJobRepository,
                                     final GigRepository gigRepository) {
        this.explorationJobRepository = explorationJobRepository;
        this.gigRepository = gigRepository;
    }

    @Override
    public List<ExplorationJobDTO> findAll() {
        return explorationJobRepository.findAll()
            .stream()
            .map(explorationJob -> mapToDTO(explorationJob, new ExplorationJobDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public ExplorationJobDTO get(final Long id) {
        return explorationJobRepository.findById(id)
            .map(explorationJob -> mapToDTO(explorationJob, new ExplorationJobDTO()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Long create(final ExplorationJobDTO explorationJobDTO) {
        final ExplorationJob explorationJob = new ExplorationJob();
        mapToEntity(explorationJobDTO, explorationJob);
        return explorationJobRepository.save(explorationJob).getId();
    }

    @Override
    public void update(final Long id, final ExplorationJobDTO explorationJobDTO) {
        final ExplorationJob explorationJob = explorationJobRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(explorationJobDTO, explorationJob);
        explorationJobRepository.save(explorationJob);
    }

    @Override
    public void delete(final Long id) {
        explorationJobRepository.deleteById(id);
    }

    private ExplorationJobDTO mapToDTO(final ExplorationJob explorationJob,
                                       final ExplorationJobDTO explorationJobDTO) {
        final ExplorationResultDTO explorationResultDTO = new ExplorationResultDTO();
        explorationJobDTO.setId(explorationJob.getId());
        explorationJobDTO.setDescription(explorationJob.getDescription());
        explorationJobDTO.setCodeHash(explorationJob.getCodeHash());
        explorationJobDTO.setGig(explorationJob.getGig() == null ? null : explorationJob.getGig().getId());
        explorationJobDTO.setExplorationResultDTO(explorationJob.getExplorationJob() == null
            ? null : mapExplorationResultToDTO(explorationResultDTO, explorationJob.getExplorationJob()));
        return explorationJobDTO;
    }

    private ExplorationJob mapToEntity(final ExplorationJobDTO explorationJobDTO,
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
        return explorationJob;
    }

    private ExplorationResultDTO mapExplorationResultToDTO(final ExplorationResultDTO explorationResultDTO,
                                                           final ExplorationResult explorationResult) {
        explorationResultDTO.setId(explorationResult.getId());
        explorationResultDTO.setLocation(explorationResult.getLocation());
        explorationResultDTO.setStorateType(explorationResult.getStorateType());
        explorationResultDTO.setExplorationJob(explorationResult.getExplorationJob().getId());
        return explorationResultDTO;
    }
}
