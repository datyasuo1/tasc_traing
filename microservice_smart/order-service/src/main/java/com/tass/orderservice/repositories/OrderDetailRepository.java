package com.tass.orderservice.repositories;


import com.tass.orderservice.entities.OrderDetail;
import com.tass.orderservice.entities.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> , JpaSpecificationExecutor<OrderDetail> {
    List<OrderDetail> findAllByOrderId(Long id);


}

