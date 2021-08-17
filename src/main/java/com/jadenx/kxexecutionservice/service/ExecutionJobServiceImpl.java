package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.ExecutionJob;
import com.jadenx.kxexecutionservice.domain.ExecutionResult;
import com.jadenx.kxexecutionservice.domain.Gig;
import com.jadenx.kxexecutionservice.domain.Order;
import com.jadenx.kxexecutionservice.model.ExecutionJobDTO;
import com.jadenx.kxexecutionservice.model.ExecutionResultDTO;
import com.jadenx.kxexecutionservice.repos.ExecutionJobRepository;
import com.jadenx.kxexecutionservice.repos.GigRepository;
import com.jadenx.kxexecutionservice.repos.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExecutionJobServiceImpl implements ExecutionJobService {

    private final ExecutionJobRepository executionJobRepository;
    private final GigRepository gigRepository;
    private final OrderRepository orderRepository;

    public ExecutionJobServiceImpl(final ExecutionJobRepository executionJobRepository,
                                   final GigRepository gigRepository, final OrderRepository orderRepository) {
        this.executionJobRepository = executionJobRepository;
        this.gigRepository = gigRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<ExecutionJobDTO> findAll() {
        return executionJobRepository.findAll()
            .stream()
            .map(executionJob -> mapToDTO(executionJob, new ExecutionJobDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public ExecutionJobDTO get(final Long id) {
        return executionJobRepository.findById(id)
            .map(executionJob -> mapToDTO(executionJob, new ExecutionJobDTO()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Long create(final ExecutionJobDTO executionJobDTO) {
        final ExecutionJob executionJob = new ExecutionJob();
        mapToEntity(executionJobDTO, executionJob);
        return executionJobRepository.save(executionJob).getId();
    }

    @Override
    public void update(final Long id, final ExecutionJobDTO executionJobDTO) {
        final ExecutionJob executionJob = executionJobRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(executionJobDTO, executionJob);
        executionJobRepository.save(executionJob);
    }

    @Override
    public void delete(final Long id) {
        executionJobRepository.deleteById(id);
    }

    private ExecutionJobDTO mapToDTO(final ExecutionJob executionJob,
                                     final ExecutionJobDTO executionJobDTO) {
        final ExecutionResultDTO executionResultDTO = new ExecutionResultDTO();
        executionJobDTO.setId(executionJob.getId());
        executionJobDTO.setPriceToken(executionJob.getPriceToken());
        executionJobDTO.setDescription(executionJob.getDescription());
        executionJobDTO.setExecutionType(executionJob.getExecutionType());
        executionJobDTO.setWorkerpool(executionJob.getWorkerpool());
        executionJobDTO.setWorker(executionJob.getWorker());
        executionJobDTO.setGig(executionJob.getGig() == null ? null : executionJob.getGig().getId());
        executionJobDTO.setOrder(executionJob.getOrder() == null ? null : executionJob.getOrder().getId());
        executionJobDTO.setExecutionResultDTO(executionJob.getExecutionResult() == null ? null :
            mapExecutionResultToDTO(executionJob.getExecutionResult(),executionResultDTO));
        return executionJobDTO;
    }

    private ExecutionJob mapToEntity(final ExecutionJobDTO executionJobDTO,
                                     final ExecutionJob executionJob) {
        executionJob.setPriceToken(executionJobDTO.getPriceToken());
        executionJob.setDescription(executionJobDTO.getDescription());
        executionJob.setExecutionType(executionJobDTO.getExecutionType());
        executionJob.setWorkerpool(executionJobDTO.getWorkerpool());
        executionJob.setWorker(executionJobDTO.getWorker());
        if (executionJobDTO.getGig() != null
            && (executionJob.getGig() == null
            || !executionJob.getGig().getId().equals(executionJobDTO.getGig()))) {
            final Gig gig = gigRepository.findById(executionJobDTO.getGig())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "gig not found"));
            executionJob.setGig(gig);
        }
        if (executionJobDTO.getOrder() != null
            && (executionJob.getOrder() == null
            || !executionJob.getOrder().getId().equals(executionJobDTO.getOrder()))) {
            final Order order = orderRepository.findById(executionJobDTO.getOrder())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found"));
            executionJob.setOrder(order);
        }
        return executionJob;
    }

    private ExecutionResultDTO mapExecutionResultToDTO(final ExecutionResult executionResult,
                                                       final ExecutionResultDTO executionResultDTO) {
        executionResultDTO.setId(executionResult.getId());
        executionResultDTO.setLocation(executionResult.getLocation());
        executionResultDTO.setStorageType(executionResult.getStorageType());
        executionResultDTO.setTransactionId(executionResult.getTransactionId());
        executionResultDTO.setBlockchianIdentifier(executionResult.getBlockchianIdentifier());
        executionResultDTO.setExecutionJob(executionResult.getExecutionJob().getId());
        return executionResultDTO;
    }
}
