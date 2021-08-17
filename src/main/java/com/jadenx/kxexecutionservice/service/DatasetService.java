package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.model.DatasetDTO;

import java.util.List;


public interface DatasetService {

    List<DatasetDTO> findAll();

    DatasetDTO get(final Long id);

    Long create(final DatasetDTO datasetDTO);

    void update(final Long id, final DatasetDTO datasetDTO);

    void delete(final Long id);

}
