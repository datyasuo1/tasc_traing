package com.example.smart_shop.controller.User;

import com.example.smart_shop.controller.BaseController;
import com.example.smart_shop.databases.entities.Brand;
import com.example.smart_shop.databases.entities.Category;
import com.example.smart_shop.databases.entities.Color;
import com.example.smart_shop.databases.entities.Product;
import com.example.smart_shop.databases.myenum.ProductStatus;
import com.example.smart_shop.model.response.ApiException;
import com.example.smart_shop.model.response.BaseResponse;
import com.example.smart_shop.service.ProductService;
import com.example.smart_shop.specification.Specifications;
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
@RequestMapping(path = "/api/v1")
public class ProductController extends BaseController {
    @Autowired
    ProductService productService;

    @GetMapping(path = "/product")
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
    ) throws ApiException {
        Specification<Product> specification = Specifications.proSpec(name, price,from,to, category, brand, color, status, page, limit);
        return productService.findAllAndSearch(specification, page, limit);
    }
    @RequestMapping(method = RequestMethod.GET, path = "/product/{id}")
    public ResponseEntity<BaseResponse> findProduct(@PathVariable Long id) throws ApiException {
        return createdResponse(productService.findProductByIdActive(id));
    }
}
