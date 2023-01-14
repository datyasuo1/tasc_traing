package com.tass.orderservice.entities.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private CartItemIdDTO id;
    private int quantity;

    private long shoppingCartId;

    private long product;
}
