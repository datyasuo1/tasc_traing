package com.tass.common.model.dto.product;

import com.tass.common.model.myenums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private long id;
    private String name;
    private BigDecimal price;
    private int qty;

    private ProductStatus status;
}
