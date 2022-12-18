package com.example.smart_shop.controller.User;

import com.example.smart_shop.controller.BaseController;
import com.example.smart_shop.databases.entities.User;
import com.example.smart_shop.model.response.ApiException;
import com.example.smart_shop.model.response.BaseResponse;
import com.example.smart_shop.service.FavoriteService;
import com.example.smart_shop.service.UserService;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(path = "/api/v1/users/favorite")
public class FavoriteController extends BaseController {

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    UserService userService;

    @PostMapping(path = "/add")
    public ResponseEntity<BaseResponse> addFavorite(Principal principal, @Param("productId") Long productId) throws ApiException {
        Optional<User> optionalUser = userService.findByNameActive(principal.getName());
        if (!optionalUser.isPresent()) {
            throw new ApiException(100, "User not found");
        }
        BaseResponse favorite = favoriteService.addFavorite(optionalUser.get(),productId);
        if(favorite == null){
            throw new ApiException(100, "Add fail");
        }
        return createdResponse(favorite);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAll(Principal principal) throws ApiException{
        Optional<User> optionalUser = userService.findByNameActive(principal.getName());
        if (!optionalUser.isPresent()) {
            throw new ApiException(100, "User not found");
        }
        User user = optionalUser.get();
        return createdResponse(favoriteService.getAllFavorite(user.getId()));
    }
    @PutMapping(path = "/delete")
    public ResponseEntity<BaseResponse> deleteFavorite(@Param("favoriteId") long favoriteId,@Param("productId")Long productId,Principal principal) throws ApiException{
        Optional<User> optionalUser = userService.findByNameActive(principal.getName());
        if (!optionalUser.isPresent()) {
            throw new ApiException(100, "User not found");
        }
        BaseResponse favorite = favoriteService.unFavorite(favoriteId,productId);
        if(favorite == null){
            throw new ApiException(100, "unFavorite fail");
        }
        return ResponseEntity.ok(favorite) ;
    }
}
