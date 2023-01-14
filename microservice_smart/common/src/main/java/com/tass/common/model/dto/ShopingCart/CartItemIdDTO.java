package com.tass.common.model.dto.ShopingCart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemIdDTO implements Serializable{
    private long shoppingCartId;
    private long productId;



}
