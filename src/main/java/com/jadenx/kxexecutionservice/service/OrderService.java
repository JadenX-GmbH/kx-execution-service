package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.model.OrderDTO;

import java.util.List;


public interface OrderService {

    List<OrderDTO> findAll();

    OrderDTO get(final Long id);

    Long create(final OrderDTO orderDTO);

    void update(final Long id, final OrderDTO orderDTO);

    void delete(final Long id);

}
