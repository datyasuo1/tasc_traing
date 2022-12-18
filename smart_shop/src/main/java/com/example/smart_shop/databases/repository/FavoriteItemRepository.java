package com.example.smart_shop.databases.repository;

import com.example.smart_shop.databases.entities.FavoriteItem;
import com.example.smart_shop.databases.entities.FavoriteItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteItemRepository extends JpaRepository<FavoriteItem, FavoriteItemId> {

    List<FavoriteItem> findAllByFavoriteId(Long favoriteId);

//    void deleteAllByShoppingCartId(Long shoppingCartId );
}
