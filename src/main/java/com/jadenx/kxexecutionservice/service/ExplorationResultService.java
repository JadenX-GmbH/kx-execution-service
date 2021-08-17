package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.model.ExplorationResultDTO;

import java.util.List;


public interface ExplorationResultService {

    List<ExplorationResultDTO> findAll();

    ExplorationResultDTO get(final Long id);

    Long create(final ExplorationResultDTO explorationResultDTO);

    void update(final Long id, final ExplorationResultDTO explorationResultDTO);

    void delete(final Long id);

}
