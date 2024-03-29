package com.tass.shopingcartservice.entities;

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
public class CartItemId implements Serializable {
    @Column(name = "shoppingCart_id")
    private long shoppingCartId;
    @Column(name = "product_id")
    private long productId;
}