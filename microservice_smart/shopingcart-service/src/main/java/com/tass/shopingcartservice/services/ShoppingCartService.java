package com.tass.shopingcartservice.services;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponseV2;
import com.tass.common.model.ERROR;
import com.tass.common.model.dto.order.OrderDTO;
import com.tass.common.model.dto.product.ProductDTO;
import com.tass.common.model.myenums.ProductStatus;
import com.tass.common.model.myenums.ShoppingCartStatus;
import com.tass.common.model.userauthen.UserDTO;
import com.tass.common.utils.JsonHelper;
import com.tass.shopingcartservice.connector.ProductConnector;
import com.tass.shopingcartservice.entities.CartItem;
import com.tass.shopingcartservice.entities.CartItemId;
import com.tass.shopingcartservice.entities.ShoppingCart;
import com.tass.shopingcartservice.repositories.CartItemRepository;
import com.tass.shopingcartservice.repositories.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ShoppingCartService extends BaseService{

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ProductConnector productConnector;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ResdisPusherMessageService resdisPusherMessageService;


    @Autowired
    @Qualifier("shoppingcart")
    ChannelTopic channelTopic;

    @Autowired
    RabbitTemplate rabbitTemplate;


    public BaseResponseV2 addToShoppingCart(Long userId, Long productId, int quantity){

        UserDTO userDTO = getUserDTO();


        BaseResponseV2<ProductDTO> productInfoResponse = productConnector.getProductById(productId);

        if (!productInfoResponse.isSuccess()){
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }
        ProductDTO productDTO = productInfoResponse.getData();

        if (productDTO == null){
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        if (productDTO.getStatus() != ProductStatus.ACTIVE){

            throw new ApplicationException(ERROR.PRODUCT_NOT_ACTIVE);
        }

        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByUserId(userDTO.getUserId());
        if (optionalShoppingCart.isPresent()){
            Set<CartItem> cartItems = optionalShoppingCart.get().getItems();
            boolean exits = false;
            for (CartItem item:cartItems
            ) {
                if (item.getProduct()==(productId)){
                    exits = true;
                    item.setQuantity(item.getQuantity() + quantity);
                    if (item.getQuantity() > productDTO.getQty()){
                        item.setQuantity(productDTO.getQty());
                    }
                }
            }
            if (!exits){
                CartItem item = new CartItem();
                item.setProduct(productDTO.getId());
                item.setQuantity(quantity);
                item.setShoppingCart(optionalShoppingCart.get());
                CartItemId cartItemId = new CartItemId(optionalShoppingCart.get().getId(), productId);
                item.setId(cartItemId);
                cartItems.add(item);
            }
            optionalShoppingCart.get().setItems(cartItems);
            optionalShoppingCart.get().setStatus(ShoppingCartStatus.SUCCESS);
//            return new BaseResponseV2<>(shoppingCartRepository.save(optionalShoppingCart.get()));
            shoppingCartRepository.save(optionalShoppingCart.get());

//            String message = JsonHelper.toString(shoppingCartDTO);
//            resdisPusherMessageService.sendMessage(message , channelTopic);
//            log.info("message",message);
            return new BaseResponseV2<>();

        } else {
            ShoppingCart shoppingCart = new ShoppingCart();
            Set<CartItem> cartItems = new HashSet<>();
            CartItem cartItem = new CartItem();
            cartItem.setProduct(productDTO.getId());
            if (quantity > productDTO.getQty()){
                quantity = productDTO.getQty();
            }
            cartItem.setQuantity(quantity);
            cartItems.add(cartItem);
            CartItemId cartItemId = new CartItemId(shoppingCart.getId(), productId);
            cartItem.setId(cartItemId);
            cartItem.setShoppingCart(shoppingCart);
            shoppingCart.setItems(cartItems);
            shoppingCart.setUserId(userDTO.getUserId());
            shoppingCart.setStatus(ShoppingCartStatus.SUCCESS);
//            return new BaseResponseV2<>(shoppingCartRepository.save(shoppingCart));
            shoppingCartRepository.save(shoppingCart);
//            String message = JsonHelper.toString(shoppingCartDTO);
//            resdisPusherMessageService.sendMessage(message , channelTopic);
//            log.info("message",message);
            return new BaseResponseV2<>(shoppingCart);
        }
    }

    public BaseResponseV2 getAllCart(Long userId){
        UserDTO userDTO = getUserDTO();
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByUserId(userDTO.getUserId());
        if (!shoppingCart.isPresent()){
            return null;
        }
        Long shoppingCartId = shoppingCart.get().getId();
        List<CartItem> cartItemList = cartItemRepository.findAllByShoppingCartId(shoppingCartId);
        return new BaseResponseV2<>(cartItemList);
    }
    public ShoppingCart deleteCartItem(Long shoppingCartId, Long productId){
        BaseResponseV2<ProductDTO> productInfoResponse = productConnector.getProductById(productId);

        if (!productInfoResponse.isSuccess()){
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }
        ProductDTO productDTO = productInfoResponse.getData();

        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(shoppingCartId);
        if (!optionalShoppingCart.isPresent()){
            return null;
        }
        ShoppingCart shoppingCart = optionalShoppingCart.get();
        Set<CartItem> cartItems = shoppingCart.getItems();
        boolean exits = false;
        for (CartItem item:cartItems
        ) {
            if (item.getProduct()==(productDTO.getId())){
                exits = true;
                cartItems.remove(item);
                CartItemId cartItemId = new CartItemId(shoppingCartId, productId);
                cartItemRepository.deleteById(cartItemId);
            }
        }
        if (!exits){
            return null;
        }
        shoppingCart.setItems(cartItems);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public ShoppingCart updateQuantity(Long shoppingCartId, Long productId, int quantity){
        BaseResponseV2<ProductDTO> productInfoResponse = productConnector.getProductById(productId);

        if (!productInfoResponse.isSuccess()){
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }
        ProductDTO productDTO = productInfoResponse.getData();

        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(shoppingCartId);
        if (!optionalShoppingCart.isPresent()){
            return null;
        }
        ShoppingCart shoppingCart = optionalShoppingCart.get();
        Set<CartItem> cartItems = shoppingCart.getItems();
        for (CartItem item:cartItems
        ) {
            if (item.getProduct()==(productId)){
                item.setQuantity(quantity);
                shoppingCart.setItems(cartItems);
                cartItemRepository.save(item);
            }
        }
        return shoppingCartRepository.save(shoppingCart);
    }

    public void deleteAllCart(Long shoppingCartId){
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(shoppingCartId);
        if (!optionalShoppingCart.isPresent()){
            return;
        }
        ShoppingCart shoppingCart = optionalShoppingCart.get();
        Set<CartItem> cartItems = shoppingCart.getItems();
        for (CartItem item:cartItems
        ) {
            cartItemRepository.deleteAllByShoppingCartId(item.getShoppingCart().getId());
        }
    }


    @RabbitListener(queues = {"${spring.rabbitmq.queue.order}"})
    private void listenMessage(OrderDTO orderDTO){
        log.info("data " + orderDTO.getProductId());
        deleteCartItem(orderDTO.getShoppingCartId(), orderDTO.getProductId());
    }


}