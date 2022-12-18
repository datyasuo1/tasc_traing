package com.example.smart_shop.controller.Admin;

import com.example.smart_shop.controller.BaseController;
import com.example.smart_shop.databases.entities.Brand;
import com.example.smart_shop.databases.entities.Category;
import com.example.smart_shop.databases.entities.Color;
import com.example.smart_shop.databases.entities.Product;
import com.example.smart_shop.databases.myenum.ProductStatus;
import com.example.smart_shop.model.request.ProductRequest;
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

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(path = "/api/v1/admins")
public class ProductsController extends BaseController {
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
                                 @RequestParam(value = "status", required = false) ProductStatus status,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "limit", defaultValue = "10") int limit
    ) throws ApiException {
        Specification<Product> specification = Specifications.proSpec(name, price,from,to, category, brand, color, status, page, limit);
        return productService.findAllAndSearch(specification, page, limit);
    }

    @PostMapping(path = "/product")
    public ResponseEntity<BaseResponse> create(@Valid @RequestBody ProductRequest productRequest) throws ApiException {
        return createdResponse(productService.createProducts(productRequest));
    }

    @PutMapping(path = "/product/{id}")
    public ResponseEntity<BaseResponse> update(@Valid @RequestBody ProductRequest productRequest, @PathVariable Long id) throws ApiException {
        return createdResponse(productService.updateProducts(id, productRequest));
    }

    @PutMapping(path = "/product/undelete/{id}")
    public ResponseEntity<BaseResponse> change(@PathVariable Long id) throws ApiException {
        return createdResponse(productService.unDeleteProduct(id));
    }

    @PutMapping(path = "/product/delete/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long id) throws ApiException {
        return createdResponse(productService.deleteProduct(id));
    }

//    @GetMapping(path = "/product/{id}")
    @RequestMapping(method = RequestMethod.GET, path = "/product/{id}")
    public ResponseEntity<BaseResponse> findProduct(@PathVariable Long id) throws ApiException {
        return createdResponse(productService.findProductById(id));
    }

}
