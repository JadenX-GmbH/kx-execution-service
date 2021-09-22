package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.*;
import com.jadenx.kxexecutionservice.model.ExecutionJobDTO;
import com.jadenx.kxexecutionservice.model.ExplorationJobDTO;
import com.jadenx.kxexecutionservice.model.GigDTO;
import com.jadenx.kxexecutionservice.repos.DatasetRepository;
import com.jadenx.kxexecutionservice.repos.GigRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Transactional
@Service
public class GigServiceImpl implements GigService {

    private final GigRepository gigRepository;
    private final DatasetRepository datasetRepository;

    public GigServiceImpl(final GigRepository gigRepository,
                          final DatasetRepository datasetRepository) {
        this.gigRepository = gigRepository;
        this.datasetRepository = datasetRepository;
    }

    @Override
    public List<GigDTO> findAll(final String uid) {
        return gigRepository.findByDataOwnerOrSpecialist(UUID.fromString(uid), UUID.fromString(uid))
            .stream()
            .map(gig -> mapToDTO(gig, new GigDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public GigDTO get(final Long id, final String uid) {
        return gigRepository.findById(id)
            .filter(filterByDataOwnerOrSpecialistPredicate(uid))
            .map(gig -> mapToDTO(gig, new GigDTO()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<ExecutionJobDTO> getExecutionJobs(final Long id, final String uid) {
        var executionJobsSet = gigRepository.findById(id)
            .filter(filterByDataOwnerOrSpecialistPredicate(uid))
            .map(Gig::getGigExecutionJobs)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return executionJobsSet.stream()
            .map(executionJob -> mapExecutionJobToDTO(executionJob, new ExecutionJobDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public List<ExplorationJobDTO> getExplorationJobs(final Long id, final String uid) {
        var executionJobsSet = gigRepository.findById(id)
            .filter(filterByDataOwnerOrSpecialistPredicate(uid))
            .map(Gig::getGigExplorationJobs)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return executionJobsSet.stream()
            .map(explorationJob -> mapExplorationJobToDTO(explorationJob, new ExplorationJobDTO()))
            .collect(Collectors.toList());
    }

    private ExplorationJobDTO mapExplorationJobToDTO(
        final ExplorationJob explorationJob, final ExplorationJobDTO explorationJobDTO) {
        explorationJobDTO.setId(explorationJob.getId());
        explorationJobDTO.setDescription(explorationJob.getDescription());
        explorationJobDTO.setCodeHash(explorationJob.getCodeHash());
        explorationJobDTO.setGig(explorationJob.getGig() == null ? null : explorationJob.getGig().getId());
        return explorationJobDTO;
    }

    @Override
    public Long create(final GigDTO gigDTO) {
        final Gig gig = new Gig();
        mapToEntity(gigDTO, gig);
        return gigRepository.save(gig).getId();
    }

    @Override
    public void update(final Long id, final GigDTO gigDTO) {
        final Gig gig = gigRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(gigDTO, gig);
        gigRepository.save(gig);
    }

    @Override
    public void delete(final Long id) {
        gigRepository.deleteById(id);
    }

    private GigDTO mapToDTO(final Gig gig, final GigDTO gigDTO) {
        gigDTO.setId(gig.getId());
        gigDTO.setDataOwner(gig.getDataOwner());
        gigDTO.setSpecialist(gig.getSpecialist());
        gigDTO.setGigDatasets(gig.getGigDatasetDatasets() == null ? null : gig.getGigDatasetDatasets().stream()
            .map(Dataset::getId)
            .collect(Collectors.toList()));
        return gigDTO;
    }

    private Gig mapToEntity(final GigDTO gigDTO, final Gig gig) {
        gig.setId(gigDTO.getId());
        gig.setDataOwner(gigDTO.getDataOwner());
        gig.setSpecialist(gigDTO.getSpecialist());
        if (gigDTO.getGigDatasets() != null) {
            final List<Dataset> gigDatasets = datasetRepository.findAllById(gigDTO.getGigDatasets());
            if (gigDatasets.size() != gigDTO.getGigDatasets().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of gigDatasets not found");
            }
            gig.setGigDatasetDatasets(new HashSet<>(gigDatasets));
        }
        return gig;
    }

    private ExecutionJobDTO mapExecutionJobToDTO(final ExecutionJob executionJob,
                                                 final ExecutionJobDTO executionJobDTO) {
        executionJobDTO.setId(executionJob.getId());
        executionJobDTO.setPriceToken(executionJob.getPriceToken());
        executionJobDTO.setDescription(executionJob.getDescription());
        executionJobDTO.setExecutionType(executionJob.getExecutionType());
        executionJobDTO.setWorkerpool(executionJob.getWorkerpool());
        executionJobDTO.setWorker(executionJob.getWorker());
        executionJobDTO.setGig(executionJob.getGig() == null ? null : executionJob.getGig().getId());
        return executionJobDTO;
    }


    private Predicate<Gig> filterByDataOwnerOrSpecialistPredicate(final String uid) {
        return gig -> gig.getDataOwner()
            .equals(UUID.fromString(uid))
            || (gig.getSpecialist() != null && gig.getSpecialist()
            .equals(UUID.fromString(uid)));
    }
}
