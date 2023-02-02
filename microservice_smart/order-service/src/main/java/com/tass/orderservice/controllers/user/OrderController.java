package com.tass.orderservice.controllers.user;

import com.tass.common.model.BaseResponseV2;
import com.tass.common.model.dto.ShopingCart.ShoppingCartDTO;
import com.tass.common.model.myenums.OrderStatus;
import com.tass.common.model.userauthen.UserDTO;
import com.tass.orderservice.connector.ShoppingCartConnector;
import com.tass.orderservice.entities.Order;
import com.tass.orderservice.entities.OrderDetail;
import com.tass.orderservice.services.*;
import com.tass.orderservice.spec.User.Specifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/order")
public class OrderController extends BaseService {




    @Autowired
    private OrderService orderService;


    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    ShoppingCartConnector shoppingCartConnector;




    @RequestMapping(method = RequestMethod.POST, path = "/checkout")
    public BaseResponseV2<?> placeOrder(@RequestParam long shoppingCartId) {

        if (shoppingCartId > 0) {
            return new BaseResponseV2<>(orderService.placeOrder(shoppingCartId));
//                BaseResponseV2<Order> result = orderService.handleEventOrder(shoppingCartId);
        } else {
            return new BaseResponseV2<>("Fails");
        }
    }

    @RequestMapping( method = RequestMethod.POST,path = "/getmyorder")
    public Page<Order> searchOrder(
                                   @RequestParam(value = "status", required = false) OrderStatus status,
                                   @RequestParam(value = "userId", required = false) Long userId,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {
//        UserDTO userDTO = getUserDTO();
//        userDTO.getUserId();
        Specification<Order> specification = Specifications.OrderSpec(status,userId,page,limit);
        return orderService.searchAllForAdmin(specification,page,limit);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BaseResponseV2 findByOrderId(@PathVariable Long id) {
        List<OrderDetail> order = orderDetailService.findByOrder(id);
        return new BaseResponseV2<>(order);
    }
}
