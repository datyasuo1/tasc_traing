package com.example.smart_shop.model.request;

import com.example.smart_shop.databases.myenum.ProductStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BrandRequest {
    @NotEmpty(message = "name missing")
    private String name;
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus status;
}
