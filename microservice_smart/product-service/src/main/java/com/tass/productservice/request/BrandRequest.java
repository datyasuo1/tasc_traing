package com.tass.productservice.request;

import com.tass.common.model.myenums.ProductStatus;
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
