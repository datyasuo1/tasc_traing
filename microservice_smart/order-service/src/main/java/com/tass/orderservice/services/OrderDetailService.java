package com.tass.orderservice.services;



import com.tass.orderservice.entities.OrderDetail;
import com.tass.orderservice.repositories.OrderDetailRepository;
import com.tass.orderservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrderRepository orderRepository;

//    public Optional<OrderDetail> findById() {
//        return orderDetailRepository.findById(id);
//    }
    public Page<OrderDetail> searchAllForAdmin(Specification<OrderDetail> specification, int page, int limit){
        return orderDetailRepository.findAll(specification, PageRequest.of(page, limit, Sort.by("updatedAt").descending()));
    }

    public List<OrderDetail> findByOrder(Long id ){
        return  orderDetailRepository.findAllByOrderId(id);
    }


}
