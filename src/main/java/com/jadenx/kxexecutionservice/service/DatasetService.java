package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.model.DatasetDTO;
import com.jadenx.kxexecutionservice.model.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface DatasetService {

    List<DatasetDTO> findAll();

    PaginatedResponse<?> findAllByUser(UUID uid , Pageable pageable);

    PaginatedResponse<?> findAllByGig(Long id, UUID uid, Pageable pageable);

    DatasetDTO get(final Long id);



    Long create(final DatasetDTO datasetDTO);

    void update(final Long id, final DatasetDTO datasetDTO);

    void delete(final Long id);

    PaginatedResponse<?> findAllByDataOwner(UUID uid, Pageable pageable);
}
