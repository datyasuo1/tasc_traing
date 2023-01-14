package com.tass.productservice.request;


import com.tass.common.model.myenums.ProductStatus;
import com.tass.productservice.entities.Brand;
import com.tass.productservice.entities.Category;
import com.tass.productservice.entities.Color;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequest {
    @NotEmpty(message = "name missing")
    private String name;
    @NotEmpty(message = "barcode missing")
    private String barcode;
    @NotEmpty(message = "description missing")
    private String description;
    @NotEmpty(message = "image missing")
    private String images;
    @NotEmpty(message = "detail missing")
    private String detail;
//    @NotEmpty(message = "price missing")
    private BigDecimal price;
//    @NotEmpty(message = "qty missing")
    private int qty;
//    @NotEmpty(message = "sold missing")
    private int sold;

    private Category category;

    private Brand brand;

    @Enumerated(EnumType.ORDINAL)
    private ProductStatus status;
    private Color color;
}
