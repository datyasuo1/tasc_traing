package com.tass.shopingcartservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tass.common.model.dto.product.ProductDTO;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart_item")
public class CartItem {
    @EmbeddedId
    private CartItemId id;
    private int quantity;
    @ManyToOne
    @MapsId("shoppingCartId")
    @JoinColumn(name = "shoppingCart_id", referencedColumnName = "id")
    @JsonBackReference
    private ShoppingCart shoppingCart;
    @Column(name = "product")
    private long product;
//    @ManyToOne
//    @MapsId("productId")
//    @JoinColumn(name = "product_id", referencedColumnName = "id")
//    private Product product;

}