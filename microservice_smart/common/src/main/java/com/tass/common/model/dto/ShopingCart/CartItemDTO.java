package com.tass.common.model.dto.ShopingCart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO implements Serializable{
    private CartItemIdDTO id;
    private int quantity;

    private long shoppingCartId;

    private long product;
}
