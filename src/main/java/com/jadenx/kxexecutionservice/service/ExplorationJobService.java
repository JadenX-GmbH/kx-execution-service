package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.model.ExplorationJobDTO;

import java.util.List;


public interface ExplorationJobService {

    List<ExplorationJobDTO> findAll();

    ExplorationJobDTO get(final Long id);

    Long create(final ExplorationJobDTO explorationJobDTO);

    void update(final Long id, final ExplorationJobDTO explorationJobDTO);

    void delete(final Long id);

}
