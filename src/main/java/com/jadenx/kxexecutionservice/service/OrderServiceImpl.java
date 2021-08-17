package com.jadenx.kxexecutionservice.service;

import com.jadenx.kxexecutionservice.domain.Order;
import com.jadenx.kxexecutionservice.model.OrderDTO;
import com.jadenx.kxexecutionservice.repos.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
            .stream()
            .map(order -> mapToDTO(order, new OrderDTO()))
            .collect(Collectors.toList());
    }

    @Override
    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
            .map(order -> mapToDTO(order, new OrderDTO()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getId();
    }

    @Override
    public void update(final Long id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    @Override
    public void delete(final Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setTransactionId(order.getTransactionId());
        orderDTO.setBlockchainIdentifier(order.getBlockchainIdentifier());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setTransactionId(orderDTO.getTransactionId());
        order.setBlockchainIdentifier(orderDTO.getBlockchainIdentifier());
        return order;
    }

}
