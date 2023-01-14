package com.tass.orderservice.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CartItemIdDTO implements Serializable{
    private long shoppingCartId;
    private long productId;



}
