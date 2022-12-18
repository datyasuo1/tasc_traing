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
public class CategoryRequest {
    @NotEmpty(message = "name missing")
    private String name;
    @NotEmpty(message = "thumbnail missing")
    private String thumbnail;
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus status;
}
