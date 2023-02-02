package com.tass.orderservice.services;


import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponseV2;
import com.tass.common.model.ERROR;
import com.tass.common.model.dto.ShopingCart.CartItemDTO;
import com.tass.common.model.dto.ShopingCart.CartItemIdDTO;
import com.tass.common.model.dto.ShopingCart.ShoppingCartDTO;
import com.tass.common.model.dto.order.OrderDTO;
import com.tass.common.model.dto.product.ProductDTO;
import com.tass.common.model.myenums.OrderStatus;
import com.tass.common.model.myenums.ShoppingCartStatus;
import com.tass.common.model.userauthen.UserDTO;
import com.tass.orderservice.connector.ProductConnector;
import com.tass.orderservice.connector.ShoppingCartConnector;
import com.tass.orderservice.entities.Order;
import com.tass.orderservice.entities.OrderDetail;
import com.tass.orderservice.entities.OrderDetailId;
import com.tass.orderservice.repositories.OrderDetailRepository;
import com.tass.orderservice.repositories.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Log4j2
public class OrderService extends BaseService{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductConnector productConnector;

    @Autowired
    ShoppingCartConnector shoppingCartConnector;

    @Autowired
    ResdisPusherMessageService resdisPusherMessageService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.exchange}")
    private String EXCHANGE_NAME;

    @Value("${spring.rabbitmq.routing-key.order}")
    private String ROUTING_KEY_NAME;
    @Autowired
    @Qualifier("shoppingcart")
    ChannelTopic channelTopic;
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
    public Order saveCart(Order order) {
        return orderRepository.save(order);
    }

    public Map<String, Object> findAll(Pageable pageable) {
        Map<String, Object> responses = new HashMap<>();
        Page<Order> pageTotal = orderRepository.findAllBy(pageable);
        List<Order> list = pageTotal.getContent();
        responses.put("content", list);
        responses.put("currentPage", pageTotal.getNumber() + 1);
        responses.put("totalItems", pageTotal.getTotalElements());
        responses.put("totalPage", pageTotal.getTotalPages());
        return responses;
    }

//    public void handleEventOrder(ShoppingCartDTO shoppingCartDTO){
//        // handle event order
//
//        if (shoppingCartDTO.getStatus() == ShoppingCartStatus.SUCCESS){
//            this.placeOrder(shoppingCartDTO.getId());
//            return;
//        }
//    }

    @Transactional
    public BaseResponseV2<Order> placeOrder(Long id) throws ApplicationException {
        Optional<ShoppingCartDTO> shoppingCartOptional = shoppingCartConnector.getShoppingCartById(id);
        if (shoppingCartOptional.isPresent()) {
            ShoppingCartDTO shoppingCart = shoppingCartOptional.get();

            Set<OrderDetail> orderDetailSet = new HashSet<>();
            OrderDetail dto = null;
            for (CartItemDTO cartItem:
                    shoppingCart.getItems()) {
                Long orderId = orderRepository.findMaxId();
                BaseResponseV2<ProductDTO> optionalProductDTO = productConnector.getProductById(cartItem.getProduct());
                dto = new OrderDetail();
                if (orderId == null){
                    dto.setId(new OrderDetailId(1L, cartItem.getProduct()));
                }
                dto.setId(new OrderDetailId(orderId + 1, cartItem.getProduct()));
                if (orderId == null){
                    dto.setOrder(Order.builder().id(1L).build());
                }
                dto.setOrder(Order.builder().id(orderId + 1).build());
                dto.setProduct(cartItem.getProduct());
                dto.setQuantity(cartItem.getQuantity());
                dto.setUnitPrice(optionalProductDTO.getData().getPrice());
                dto.setQuantity(cartItem.getQuantity());
                dto.setUpdatedAt(LocalDateTime.now());
                dto.setCreatedAt(LocalDateTime.now());
                orderDetailSet.add(dto);

            }
            UserDTO userDTO = getUserDTO();
            for (CartItemDTO cartItem:
                    shoppingCart.getItems()) {
                BaseResponseV2<ProductDTO> optionalProductDTO = productConnector.getProductById(cartItem.getProduct());
                Order order = Order.builder()
                    .status(OrderStatus.DONE)
                    .userId(shoppingCart.getUserId())
                    .orderDetails(orderDetailSet)
                    .isShoppingCart(true)
                    .totalPrice(optionalProductDTO.getData().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                    .build();
            order.setUpdatedAt(LocalDateTime.now());
            order.setCreatedAt(LocalDateTime.now());
            orderRepository.save(order);
            orderDetailRepository.saveAll(orderDetailSet);

                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setShoppingCartId(id);
                orderDTO.setProductId(cartItem.getProduct());
                orderDTO.setQty(cartItem.getQuantity());
                rabbitTemplate.convertAndSend(EXCHANGE_NAME,ROUTING_KEY_NAME,orderDTO);
            return new BaseResponseV2<>(order);
            }
        }
        throw new ApplicationException(ERROR.ID_NOT_FOUND,"ShoppingCart Not Fond");
//        return null;
    }
    public Page<Order> searchAllForAdmin(Specification<Order> specification, int page, int limit){
        return orderRepository.findAll(specification, PageRequest.of(page, limit,Sort.by("updatedAt").descending()));
    }

    public int totalOrder(){
        return orderRepository.findAll().size();
    }
//    public int totalOrderByStatus(int status){
//        OrderStatus status1 = OrderStatus.CONFIRMED;
//        if (status == 0){
//            status1 = OrderStatus.PENDING;
//        }
//        if (status == 2){
//            status1 = OrderStatus.CANCELLED;
//        }
//        if (status == 3){
//            status1 = OrderStatus.DONE;
//        }
//        if (status == 4){
//            status1 = OrderStatus.PROCESSING;
//        }
//        return orderRepository.findAllByStatus(status1).size();
//    }



}