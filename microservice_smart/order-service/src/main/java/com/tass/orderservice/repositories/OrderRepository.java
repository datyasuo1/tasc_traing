package com.tass.orderservice.repositories;


import com.tass.common.model.myenums.OrderStatus;
import com.tass.orderservice.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Page<Order> findAllBy(Pageable pageable);
//    Order findByOrderDetails(OrderEvent orderEvent);

    @Query("SELECT max(o.id) FROM Order o")
    Long findMaxId();

    List<Order> findAllByStatus(OrderStatus status);

}

