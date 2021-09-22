package com.jadenx.kxexecutionservice.repos;

import com.jadenx.kxexecutionservice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getAllByExecutionJob_Id(Long id);
}
