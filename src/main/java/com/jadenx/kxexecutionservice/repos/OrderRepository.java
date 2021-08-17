package com.jadenx.kxexecutionservice.repos;

import com.jadenx.kxexecutionservice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
