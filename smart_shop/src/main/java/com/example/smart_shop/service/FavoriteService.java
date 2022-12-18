package com.example.smart_shop.service;

import com.example.smart_shop.databases.entities.*;
import com.example.smart_shop.databases.myenum.ProductStatus;
import com.example.smart_shop.databases.repository.FavoriteItemRepository;
import com.example.smart_shop.databases.repository.FavoriteRepository;
import com.example.smart_shop.databases.repository.ProductRepository;
import com.example.smart_shop.databases.repository.UserRepository;
import com.example.smart_shop.model.response.ApiException;
import com.example.smart_shop.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {
    @Autowired
    FavoriteRepository favoriteRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FavoriteItemRepository favoriteItemRepository;


    public BaseResponse addFavorite(User user,Long productId)throws ApiException{
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(productId, ProductStatus.ACTIVE);
        if (!optionalProduct.isPresent()){
            return new BaseResponse<>(100,"Product not found");
        }
        Optional<Favorite> optionalFavorite = favoriteRepository.findByUserId(user.getId());
        if (optionalFavorite.isPresent()){
            Set<FavoriteItem> favoriteItems = optionalFavorite.get().getItems();
            FavoriteItem item = new FavoriteItem();
            item.setProduct(optionalProduct.get());
            item.setFavorite(optionalFavorite.get());
            FavoriteItemId favoriteItemId = new FavoriteItemId(optionalFavorite.get().getId(),productId);
            item.setId(favoriteItemId);
            favoriteItems.add(item);
            optionalFavorite.get().setItems(favoriteItems);
            return new BaseResponse<>(200,"Success");
        }else {
            Favorite favorite = new Favorite();
            Set<FavoriteItem> favoriteItems = new HashSet<>();
            FavoriteItem item = new FavoriteItem();
            item.setProduct(optionalProduct.get());
            favoriteItems.add(item);
            FavoriteItemId favoriteItemId = new FavoriteItemId(favorite.getId(),productId);
            item.setId(favoriteItemId);
            item.setFavorite(favorite);
            favorite.setUser(user);
            favoriteRepository.save(favorite);
            return new BaseResponse<>(200,"Success");
        }
    }

    public BaseResponse getAllFavorite(Long userId)throws ApiException{
        Optional<Favorite> optionalFavorite = favoriteRepository.findByUserId(userId);
        if (!optionalFavorite.isPresent()){
            return new BaseResponse<>(100,"User not found");
        }
        Long favoriteId = optionalFavorite.get().getId();
        List<FavoriteItem> favoriteItemList = favoriteItemRepository.findAllByFavoriteId(favoriteId);
        return new BaseResponse<>(200,"Success",favoriteItemList);
    }

    public BaseResponse unFavorite(Long favoriteId, Long productId)throws ApiException{
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(productId,ProductStatus.ACTIVE);
        if (!optionalProduct.isPresent()){
            return new BaseResponse<>(100,"Product not found");
        }
        Optional<Favorite> favoriteOptional = favoriteRepository.findById(favoriteId);
        if (!favoriteOptional.isPresent()){
            return new BaseResponse<>(100,"Favorite not found");
        }
        Favorite favorite = favoriteOptional.get();
        Set<FavoriteItem> favoriteItems = favorite.getItems();
        boolean exits = false;
        for (FavoriteItem item : favoriteItems
        ){
            if (item.getProduct().getId().equals(productId)){
                exits = true;
                favoriteItems.remove(item);
                FavoriteItemId favoriteItemId = new FavoriteItemId(favoriteId,productId);
                favoriteItemRepository.deleteById(favoriteItemId);
            }
        }
        if (!exits){
            return new BaseResponse(100,"Item not found");
        }
        favorite.setItems(favoriteItems);
        favoriteRepository.save(favorite);
        return new BaseResponse<>(200,"Success");
    }
}
