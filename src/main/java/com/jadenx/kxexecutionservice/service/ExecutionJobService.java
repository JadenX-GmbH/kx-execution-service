package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.model.ExecutionJobDTO;
import com.jadenx.kxexecutionservice.model.ExecutionJobPatchDTO;
import com.jadenx.kxexecutionservice.model.ProgramDTO;

import java.util.List;


public interface ExecutionJobService {

    List<ExecutionJobDTO> findAll();

    ExecutionJobDTO get(final Long id);

    Long create(final ExecutionJobDTO executionJobDTO);

    void update(final Long id, final ExecutionJobDTO executionJobDTO);

    void delete(final Long id);

    List<ProgramDTO> getProgramsByExecutionJob(final Long id);

    void patchUpdate(final Long id, final ExecutionJobPatchDTO executionJobPatchDTO);
}
