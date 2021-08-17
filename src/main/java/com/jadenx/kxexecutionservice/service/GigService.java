package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.model.ExecutionJobDTO;
import com.jadenx.kxexecutionservice.model.ExplorationJobDTO;
import com.jadenx.kxexecutionservice.model.GigDTO;

import java.util.List;


public interface GigService {

    List<GigDTO> findAll(String uid);

    GigDTO get(final Long id, String uid);

    List<ExecutionJobDTO> getExecutionJobs(Long id, String uid);

    List<ExplorationJobDTO> getExplorationJobs(final Long id, final String uid);

    Long create(final GigDTO gigDTO);

    void update(final Long id, final GigDTO gigDTO);

    void delete(final Long id);
}
