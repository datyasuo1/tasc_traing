package com.tass.orderservice.entities.dto;

import com.tass.common.model.myenums.ShoppingCartStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShoppingCartDTO {
    private long id;
    private Long userId;

    private Set<CartItemDTO> items;

    private ShoppingCartStatus status;


}
