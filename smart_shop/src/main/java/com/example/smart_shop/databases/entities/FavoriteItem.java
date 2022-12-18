package com.example.smart_shop.databases.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "favorite_item")
public class FavoriteItem {
    @EmbeddedId
    private FavoriteItemId id;
    @ManyToOne
    @MapsId("favoriteId")
    @JoinColumn(name = "favoriteId", referencedColumnName = "id")
    @JsonBackReference
    private Favorite favorite;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

}
