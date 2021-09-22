package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.model.OrderDTO;
import com.jadenx.kxexecutionservice.model.OrderPatchDTO;

import java.util.List;


public interface OrderService {

    List<OrderDTO> findAll();

    List<OrderDTO> findAllByExecutionJob(final Long id);

    OrderDTO get(final Long id);

    Long create(final OrderDTO orderDTO);

    void update(final Long id, final OrderDTO orderDTO);

    void delete(final Long id);

    void patchUpdate(final Long id, final OrderPatchDTO orderPatchDTO);

}
