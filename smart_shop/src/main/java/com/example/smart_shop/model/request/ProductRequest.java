package com.example.smart_shop.model.request;

import com.example.smart_shop.databases.entities.Brand;
import com.example.smart_shop.databases.entities.Category;
import com.example.smart_shop.databases.entities.Color;
import com.example.smart_shop.databases.entities.Role;
import com.example.smart_shop.databases.myenum.ProductStatus;
import com.example.smart_shop.databases.myenum.UserStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
