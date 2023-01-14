package com.tass.productservice.controllers.user;


import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponseV2;
import com.tass.common.model.myenums.ProductStatus;
import com.tass.productservice.entities.Brand;
import com.tass.productservice.entities.Category;
import com.tass.productservice.entities.Color;
import com.tass.productservice.entities.Product;
import com.tass.productservice.services.ProductService;
import com.tass.productservice.specification.Specifications;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(path = "/product")
public class ProductController  {
    @Autowired
    ProductService productService;

    @GetMapping()
    public Page<Product> findAll(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "price", required = false) BigDecimal price,
                                 @RequestParam(value = "from", required = false) BigDecimal from,
                                 @RequestParam(value = "to", required = false) BigDecimal to,
                                 @RequestParam(value = "category", required = false) Category category,
                                 @RequestParam(value = "brand", required = false) Brand brand,
                                 @RequestParam(value = "color", required = false) Color color,
                                 @RequestParam(value = "status", required = false,defaultValue = "ACTIVE") ProductStatus status,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "limit", defaultValue = "10") int limit
    ) throws ApplicationException {
        Specification<Product> specification = Specifications.proSpec(name, price,from,to, category, brand, color, status, page, limit);
        return productService.findAllAndSearch(specification, page, limit);
    }
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public BaseResponseV2 findProduct(@PathVariable Long id) throws ApplicationException {
        return productService.findProductByIdActive(id);
    }
}
