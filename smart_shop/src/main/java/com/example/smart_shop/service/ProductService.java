package com.example.smart_shop.service;

import com.example.smart_shop.databases.entities.Brand;
import com.example.smart_shop.databases.entities.Category;
import com.example.smart_shop.databases.entities.Color;
import com.example.smart_shop.databases.entities.Product;
import com.example.smart_shop.databases.myenum.ProductStatus;
import com.example.smart_shop.databases.repository.*;
import com.example.smart_shop.model.request.ProductRequest;
import com.example.smart_shop.model.response.ApiException;
import com.example.smart_shop.model.response.BaseResponse;
import com.example.smart_shop.model.response.ERROR;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ColorRepository colorRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    public BaseResponse createProducts(ProductRequest productRequest) throws ApiException {
        Product product = new Product();
        Optional<Product> optionalProductBarcode = productRepository.findByBarcode(productRequest.getBarcode());
        if (optionalProductBarcode.isPresent()) {
            return new BaseResponse<>(300, "Barcode already exist !");
        }
//        List<Color> colorList = new ArrayList<>();
        Optional<Color> optionalColor = colorRepository.findByName(productRequest.getColor().getName());
//            colorList.add(optionalColor.get());
        Color color = new Color();
        if (optionalColor.isPresent()) {
            product.setName(productRequest.getName());
            product.setBarcode(productRequest.getBarcode());
            product.setDescription(productRequest.getDescription());
            product.setImages(productRequest.getImages());
            product.setDetail(productRequest.getDetail());
            product.setQty(productRequest.getQty());
            product.setPrice(productRequest.getPrice());
            product.setSold(0);
            Optional<Category> categoryOptional = categoryRepository.findById(productRequest.getCategory().getId());
            product.setCategory(categoryOptional.get());
            Optional<Brand> brandOptional = brandRepository.findById(productRequest.getBrand().getId());
            product.setBrand(brandOptional.get());
            Optional<Color> optionalColoName = colorRepository.findByName(productRequest.getColor().getName());
            product.setColor(optionalColoName.get());

            product.setStatus(ProductStatus.ACTIVE);
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());
            productRepository.save(product);
            return new BaseResponse<>(200, "Success", product);
        }
        color.setName(productRequest.getColor().getName());
        colorRepository.save(color);

        product.setName(productRequest.getName());
        product.setBarcode(productRequest.getBarcode());
        product.setDescription(productRequest.getDescription());
        product.setImages(productRequest.getImages());
        product.setDetail(productRequest.getDetail());
        product.setQty(productRequest.getQty());
        product.setPrice(productRequest.getPrice());
        product.setSold(0);
        Optional<Category> categoryOptional = categoryRepository.findById(productRequest.getCategory().getId());
        product.setCategory(categoryOptional.get());
        Optional<Brand> brandOptional = brandRepository.findById(productRequest.getBrand().getId());
        product.setBrand(brandOptional.get());
        Optional<Color> optionalColoName = colorRepository.findByName(productRequest.getColor().getName());
        product.setColor(optionalColoName.get());

        product.setStatus(ProductStatus.ACTIVE);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
        return new BaseResponse<>(200, "Success", product);

    }

    private void validateRequestCreateException(ProductRequest productRequest) throws ApiException {

        if (StringUtils.isBlank(productRequest.getName())) {
            throw new ApiException(ERROR.INVALID_PARAM, "name is blank");
        }

        if (StringUtils.isBlank(productRequest.getDescription())) {
            throw new ApiException(ERROR.INVALID_PARAM, "Description is blank");
        }

        if (StringUtils.isBlank(productRequest.getImages())) {
            throw new ApiException(ERROR.INVALID_PARAM, "image is Blank");
        }
        if (StringUtils.isBlank(productRequest.getDetail())) {
            throw new ApiException(ERROR.INVALID_PARAM, "detail is Blank");
        }
        if (StringUtils.isBlank(productRequest.getCategory().getId().toString())) {
            throw new ApiException(ERROR.INVALID_PARAM, "category is Blank");
        }
        if (StringUtils.isBlank(productRequest.getBrand().getId().toString())) {
            throw new ApiException(ERROR.INVALID_PARAM, "brand is Blank");
        }
        if (StringUtils.isBlank(productRequest.getColor().toString())) {
            throw new ApiException(ERROR.INVALID_PARAM, "color is Blank");
        }

    }

    @Transactional
    public BaseResponse updateProducts(Long id, ProductRequest productRequest) throws ApiException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return new BaseResponse<>(300, "Product not found");
        }
        Product productOld = optionalProduct.get();
        //tìm color theo tên
        Optional<Color> optionalColor = colorRepository.findByName(productRequest.getColor().getName());
        Color color = new Color();
        //nếu tồn lại thì add bình thường
        if (optionalColor.isPresent()) {
            productOld.setName(productRequest.getName());
            productOld.setDescription(productRequest.getDescription());
            productOld.setImages(productRequest.getImages());
            productOld.setDetail(productRequest.getDetail());
            productOld.setQty(productRequest.getQty());
            productOld.setPrice(productRequest.getPrice());
            Optional<Category> categoryOptional = categoryRepository.findById(productRequest.getCategory().getId());
            if (!categoryOptional.isPresent()) {
                throw new ApiException(ERROR.INVALID_PARAM, "Category not found");
            }
            productOld.setCategory(categoryOptional.get());

            Optional<Brand> brandOptional = brandRepository.findById(productRequest.getBrand().getId());
            if (!brandOptional.isPresent()) {
                throw new ApiException(ERROR.INVALID_PARAM, "Brand not found");
            }
            productOld.setBrand(brandOptional.get());

            Optional<Color> optionalColoName = colorRepository.findByName(productRequest.getColor().getName());
            productOld.setColor(optionalColoName.get());

            productOld.setStatus(ProductStatus.ACTIVE);
            productOld.setUpdatedAt(LocalDateTime.now());
            productRepository.save(productOld);
            return new BaseResponse<>(200, "Success", productOld);
        }
        //nếu ko tồn tại thì save color xong mới add vào products
        color.setName(productRequest.getColor().getName());
        colorRepository.save(color);

        productOld.setName(productRequest.getName());
        productOld.setDescription(productRequest.getDescription());
        productOld.setImages(productRequest.getImages());
        productOld.setDetail(productRequest.getDetail());
        productOld.setQty(productRequest.getQty());
        productOld.setPrice(productRequest.getPrice());

        Optional<Category> categoryOptional = categoryRepository.findById(productRequest.getCategory().getId());
        if (!categoryOptional.isPresent()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Category not found");
        }
        productOld.setCategory(categoryOptional.get());

        Optional<Brand> brandOptional = brandRepository.findById(productRequest.getBrand().getId());
        if (!brandOptional.isPresent()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Brand not found");
        }
        productOld.setBrand(brandOptional.get());

        Optional<Color> optionalColoName = colorRepository.findByName(productRequest.getColor().getName());
        productOld.setColor(optionalColoName.get());

        productOld.setStatus(ProductStatus.ACTIVE);
        productOld.setUpdatedAt(LocalDateTime.now());
        productRepository.save(productOld);
        return new BaseResponse<>(200, "Success", productOld);

    }
    @Transactional
    public BaseResponse deleteProduct(Long id) throws ApiException {
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(id, ProductStatus.ACTIVE);
        if (!optionalProduct.isPresent()) {
//            return new BaseResponse(300,"Products not found");
            throw new ApiException(ERROR.INVALID_PARAM, "Products not found");
        }
        Product productDel = optionalProduct.get();
        productDel.setStatus(ProductStatus.DEACTIVE);
        productRepository.save(productDel);
        return new BaseResponse<>(200, "Success");
    }

    @Transactional
    public BaseResponse unDeleteProduct(Long id) throws ApiException {
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(id, ProductStatus.DEACTIVE);
        if (!optionalProduct.isPresent()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Products not found");
        }
        Product productDel = optionalProduct.get();
        productDel.setStatus(ProductStatus.ACTIVE);
        productRepository.save(productDel);
        return new BaseResponse<>(200, " Success");
    }

    public Page<Product> findAllAndSearch(Specification<Product> specification, int page, int limit) {
        return productRepository.findAll(specification, PageRequest.of(page, limit, Sort.by("updatedAt").descending()));
    }

    public BaseResponse findProductByIdActive(Long id) throws ApiException {
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(id, ProductStatus.ACTIVE);
        if (!optionalProduct.isPresent()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Products not found");
        }

        Product product = optionalProduct.get();

        return new BaseResponse<>(200, "Success",product);

    }
    public BaseResponse findProductById(Long id) throws ApiException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new ApiException(ERROR.INVALID_PARAM, "Products not found");
        }

        Product product = optionalProduct.get();

        return new BaseResponse<>(200, "Success",product);

    }

}
