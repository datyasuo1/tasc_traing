package com.tass.shopingcartservice.controllers;

import com.tass.common.customanotation.RequireUserLogin;
import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponseV2;
import com.tass.common.model.userauthen.UserDTO;
import com.tass.shopingcartservice.entities.ShoppingCart;
import com.tass.shopingcartservice.repositories.ShoppingCartRepository;
import com.tass.shopingcartservice.request.ShoppingCartRequest;
import com.tass.shopingcartservice.services.BaseService;
import com.tass.shopingcartservice.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(path = "/shopingcart")
public class ShoppingCartController extends BaseService {
    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @RequireUserLogin
    @RequestMapping(method = RequestMethod.POST, path = "/add")
    public BaseResponseV2 addShoppingCart( @Param("productId") long productId, @Param("quantity") int quantity)throws ApplicationException {
        UserDTO userDTO = getUserDTO();

        BaseResponseV2 shoppingCart = shoppingCartService.addToShoppingCart(userDTO.getUserId(), productId, quantity);
        return  new BaseResponseV2(shoppingCart);
    }
    @RequireUserLogin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(Principal principal) throws ApplicationException{
        UserDTO userDTO = getUserDTO();
        return ResponseEntity.ok(shoppingCartService.getAllCart(userDTO.getUserId()));
    }
    @RequireUserLogin
    @RequestMapping(method = RequestMethod.PUT, path = "/delete")
    public ResponseEntity<?> deleteCartItem(@Param("shoppingCatId") long shoppingCatId, @Param("productId") long productId, Principal principal){
        UserDTO userDTO = getUserDTO();
        ShoppingCart shoppingCart = shoppingCartService.deleteCartItem(shoppingCatId, productId);
        if (shoppingCart==null){
            return ResponseEntity.badRequest().body("delete fail");
        }
        return ResponseEntity.ok(shoppingCart) ;
    }
    @RequireUserLogin
    @RequestMapping(method = RequestMethod.PUT, path = "/update")
    public ResponseEntity<?> updateCart(@Param("shoppingCatId") long shoppingCatId, @Param("productId") long productId, @Param("quantity") int quantity){
            ShoppingCart shoppingCart = shoppingCartService.updateQuantity(shoppingCatId, productId, quantity);
        return ResponseEntity.ok(shoppingCart);
    }
    @RequireUserLogin
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Optional<ShoppingCart> findProduct(@PathVariable Long id) throws ApplicationException {
        return shoppingCartRepository.findById(id);
    }
}
