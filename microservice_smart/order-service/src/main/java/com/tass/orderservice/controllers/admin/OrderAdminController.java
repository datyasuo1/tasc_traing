package com.tass.orderservice.controllers.admin;

import com.tass.common.model.myenums.OrderStatus;
import com.tass.orderservice.entities.Order;
import com.tass.orderservice.entities.OrderDetail;
import com.tass.orderservice.services.OrderDetailService;
import com.tass.orderservice.services.OrderService;
import com.tass.orderservice.spec.User.Specifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/admin/order")
public class OrderAdminController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @RequestMapping( method = RequestMethod.GET)
    public Page<Order> searchOrder(
                                    @RequestParam(value = "status", required = false) OrderStatus status,
                                    @RequestParam(value = "userId", required = false) Long userId,
                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {
        Specification<Order> specification = Specifications.OrderSpec(status,userId,page,limit);
        return orderService.searchAllForAdmin(specification,page,limit);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findByOrderId(@PathVariable Long id) {
        List<OrderDetail> order = orderDetailService.findByOrder(id);
        return ResponseEntity.ok(order);
    }
}
