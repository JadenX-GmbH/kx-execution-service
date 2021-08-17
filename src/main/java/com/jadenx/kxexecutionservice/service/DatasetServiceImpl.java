package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.Dataset;
import com.jadenx.kxexecutionservice.model.DatasetDTO;
import com.jadenx.kxexecutionservice.repos.DatasetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DatasetServiceImpl implements DatasetService {

    private final DatasetRepository datasetRepository;

    public DatasetServiceImpl(final DatasetRepository datasetRepository) {
        this.datasetRepository = datasetRepository;
    }

    @Override
    public List<DatasetDTO> findAll() {
        return datasetRepository.findAll()
            .stream()
            .map(dataset -> mapToDTO(dataset, new DatasetDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public DatasetDTO get(final Long id) {
        return datasetRepository.findById(id)
            .map(dataset -> mapToDTO(dataset, new DatasetDTO()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Long create(final DatasetDTO datasetDTO) {
        final Dataset dataset = new Dataset();
        mapToEntity(datasetDTO, dataset);
        return datasetRepository.save(dataset).getId();
    }

    @Override
    public void update(final Long id, final DatasetDTO datasetDTO) {
        final Dataset dataset = datasetRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(datasetDTO, dataset);
        datasetRepository.save(dataset);
    }

    @Override
    public void delete(final Long id) {
        datasetRepository.deleteById(id);
    }

    private DatasetDTO mapToDTO(final Dataset dataset, final DatasetDTO datasetDTO) {
        datasetDTO.setId(dataset.getId());
        datasetDTO.setTitle(dataset.getTitle());
        datasetDTO.setDescription(dataset.getDescription());
        datasetDTO.setHash(dataset.getHash());
        datasetDTO.setType(dataset.getType());
        datasetDTO.setLocation(dataset.getLocation());
        datasetDTO.setStorageType(dataset.getStorageType());
        return datasetDTO;
    }

    private Dataset mapToEntity(final DatasetDTO datasetDTO, final Dataset dataset) {
        dataset.setTitle(datasetDTO.getTitle());
        dataset.setDescription(datasetDTO.getDescription());
        dataset.setHash(datasetDTO.getHash());
        dataset.setType(datasetDTO.getType());
        dataset.setLocation(datasetDTO.getLocation());
        dataset.setStorageType(datasetDTO.getStorageType());
        return dataset;
    }

}
