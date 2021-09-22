package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.Order;
import com.jadenx.kxexecutionservice.mapper.OrderMapper;
import com.jadenx.kxexecutionservice.mapper.OrderPatchMapper;
import com.jadenx.kxexecutionservice.model.OrderDTO;
import com.jadenx.kxexecutionservice.model.OrderPatchDTO;
import com.jadenx.kxexecutionservice.repos.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderPatchMapper orderPatchMapper;

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
            .stream()
            .map(order -> orderMapper.mapToDTO(order, new OrderDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findAllByExecutionJob(final Long id ) {
        return orderRepository.getAllByExecutionJob_Id(id)
            .stream()
            .map(order -> orderMapper.mapToDTO(order, new OrderDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
            .map(order -> orderMapper.mapToDTO(order, new OrderDTO()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        orderMapper.mapToEntity(orderDTO, order);
        return orderRepository.save(order).getId();
    }

    @Override
    public void update(final Long id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderMapper.mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    @Override
    public void delete(final Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void patchUpdate(final Long id, final OrderPatchDTO orderPatchDTO) {
        final Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderPatchMapper.mapPatchDTOToEntity(orderPatchDTO, order);
        orderRepository.save(order);
    }

}
