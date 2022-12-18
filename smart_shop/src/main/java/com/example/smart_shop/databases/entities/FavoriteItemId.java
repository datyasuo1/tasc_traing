package com.example.smart_shop.databases.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteItemId implements Serializable {
    @Column(name = "favorite_id")
    private Long favoriteId;
    @Column(name = "product_id")
    private Long productId;
}
