package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.model.ExecutionResultDTO;

import java.util.List;


public interface ExecutionResultService {

    List<ExecutionResultDTO> findAll();

    ExecutionResultDTO get(final Long id);

    Long create(final ExecutionResultDTO executionResultDTO);

    void update(final Long id, final ExecutionResultDTO executionResultDTO);

    void delete(final Long id);

}
