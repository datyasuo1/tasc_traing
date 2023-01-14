package com.tass.shopingcartservice.request;

import lombok.Data;

@Data
public class ShoppingCartRequest {
    private Long userId;
    private Long productId;
    private int quantity;
}
