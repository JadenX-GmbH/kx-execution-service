package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.model.ProgramDTO;

import java.util.List;


public interface ProgramService {

    List<ProgramDTO> findAll();

    ProgramDTO get(final Long id);

    Long create(final ProgramDTO programDTO);

    void update(final Long id, final ProgramDTO programDTO);

    void delete(final Long id);

}
