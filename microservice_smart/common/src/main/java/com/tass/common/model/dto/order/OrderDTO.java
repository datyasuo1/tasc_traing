package com.tass.common.model.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable{
    private long shoppingCartId;
    private long productId;
    private int qty;
}
