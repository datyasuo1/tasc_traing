package com.tass.productservice.services;


import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponseV2;
import com.tass.common.model.ERROR;
import com.tass.common.model.dto.order.OrderDTO;
import com.tass.common.model.myenums.ProductStatus;
import com.tass.productservice.entities.Brand;
import com.tass.productservice.entities.Category;
import com.tass.productservice.entities.Color;
import com.tass.productservice.entities.Product;
import com.tass.productservice.repositories.BrandRepository;
import com.tass.productservice.repositories.CategoryRepository;
import com.tass.productservice.repositories.ColorRepository;
import com.tass.productservice.repositories.ProductRepository;
import com.tass.productservice.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    ColorRepository colorRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    public BaseResponseV2 createProducts(ProductRequest productRequest) throws ApplicationException {
        Product product = new Product();
        Optional<Product> optionalProductBarcode = productRepository.findByBarcode(productRequest.getBarcode());
        if (optionalProductBarcode.isPresent()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Barcode already exist !");
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
            if (!categoryOptional.isPresent()) {
                throw new ApplicationException(ERROR.INVALID_PARAM, "Category not found");
            }
            product.setCategory(categoryOptional.get());
            Optional<Brand> brandOptional = brandRepository.findById(productRequest.getBrand().getId());
            if (!brandOptional.isPresent()) {
                throw new ApplicationException(ERROR.INVALID_PARAM, "Brand not found");
            }
            product.setBrand(brandOptional.get());

            Optional<Color> optionalColoName = colorRepository.findByName(productRequest.getColor().getName());
            product.setColor(optionalColoName.get());

            product.setStatus(ProductStatus.ACTIVE);
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());
            productRepository.save(product);
            return new BaseResponseV2(product);
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
        if (!categoryOptional.isPresent()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Category not found");
        }
        product.setCategory(categoryOptional.get());

        Optional<Brand> brandOptional = brandRepository.findById(productRequest.getBrand().getId());
        if (!brandOptional.isPresent()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Brand not found");
        }
        product.setBrand(brandOptional.get());

        Optional<Color> optionalColoName = colorRepository.findByName(productRequest.getColor().getName());
        product.setColor(optionalColoName.get());

        product.setStatus(ProductStatus.ACTIVE);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
        return new BaseResponseV2(product);

    }


    @Transactional
    public BaseResponseV2 updateProducts(Long id, ProductRequest productRequest) throws ApplicationException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new ApplicationException(ERROR.PRODUCT_NOT_FOUND, "Product not found");
        }
        Product productOld = optionalProduct.get();
        Optional<Product> optionalProductBarcode = productRepository.findByBarcode(productRequest.getBarcode());
        if (optionalProductBarcode.isPresent()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Barcode already exist !");}
        //tìm color theo tên
        Optional<Color> optionalColor = colorRepository.findByName(productRequest.getColor().getName());
        Color color = new Color();
        //nếu tồn lại thì add bình thường
        if (optionalColor.isPresent()) {
            productOld.setName(productRequest.getName());
            productOld.setDescription(productRequest.getDescription());
            productOld.setBarcode(productRequest.getBarcode());
            productOld.setImages(productRequest.getImages());
            productOld.setDetail(productRequest.getDetail());
            productOld.setQty(productRequest.getQty());
            productOld.setPrice(productRequest.getPrice());
            Optional<Category> categoryOptional = categoryRepository.findById(productRequest.getCategory().getId());
            if (!categoryOptional.isPresent()) {
                throw new ApplicationException(ERROR.INVALID_PARAM, "Category not found");
            }
            productOld.setCategory(categoryOptional.get());

            Optional<Brand> brandOptional = brandRepository.findById(productRequest.getBrand().getId());
            if (!brandOptional.isPresent()) {
                throw new ApplicationException(ERROR.INVALID_PARAM, "Brand not found");
            }
            productOld.setBrand(brandOptional.get());

            Optional<Color> optionalColoName = colorRepository.findByName(productRequest.getColor().getName());
            productOld.setColor(optionalColoName.get());

            productOld.setStatus(ProductStatus.ACTIVE);
            productOld.setUpdatedAt(LocalDateTime.now());
            productRepository.save(productOld);
            return new BaseResponseV2<>(productOld);
        }
        //nếu ko tồn tại thì save color xong mới add vào products
        color.setName(productRequest.getColor().getName());
        colorRepository.save(color);

        productOld.setName(productRequest.getName());
        productOld.setDescription(productRequest.getDescription());
        productOld.setBarcode(productRequest.getBarcode());
        productOld.setImages(productRequest.getImages());
        productOld.setDetail(productRequest.getDetail());
        productOld.setQty(productRequest.getQty());
        productOld.setPrice(productRequest.getPrice());

        Optional<Category> categoryOptional = categoryRepository.findById(productRequest.getCategory().getId());
        if (!categoryOptional.isPresent()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Category not found");
        }
        productOld.setCategory(categoryOptional.get());

        Optional<Brand> brandOptional = brandRepository.findById(productRequest.getBrand().getId());
        if (!brandOptional.isPresent()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Brand not found");
        }
        productOld.setBrand(brandOptional.get());

        Optional<Color> optionalColoName = colorRepository.findByName(productRequest.getColor().getName());
        productOld.setColor(optionalColoName.get());

        productOld.setStatus(ProductStatus.ACTIVE);
        productOld.setUpdatedAt(LocalDateTime.now());
        productRepository.save(productOld);
        return new BaseResponseV2<>(productOld);

    }

    @Transactional
    public BaseResponseV2 deleteProduct(Long id) throws ApplicationException {
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(id, ProductStatus.ACTIVE);
        if (!optionalProduct.isPresent()) {
//            return new BaseResponse(300,"Products not found");
            throw new ApplicationException(ERROR.INVALID_PARAM, "Products not found");
        }
        Product productDel = optionalProduct.get();
        productDel.setStatus(ProductStatus.DEACTIVE);
        productRepository.save(productDel);
        return new BaseResponseV2();
    }

    @Transactional
    public BaseResponseV2 unDeleteProduct(Long id) throws ApplicationException {
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(id, ProductStatus.DEACTIVE);
        if (!optionalProduct.isPresent()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Products not found");
        }
        Product productDel = optionalProduct.get();
        productDel.setStatus(ProductStatus.ACTIVE);
        productRepository.save(productDel);
        return new BaseResponseV2();
    }

    public Page<Product> findAllAndSearch(Specification<Product> specification, int page, int limit) {
        return productRepository.findAll(specification, PageRequest.of(page, limit, Sort.by("updatedAt").descending()));
    }

    public BaseResponseV2 findProductByIdActive(Long id) throws ApplicationException {
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(id, ProductStatus.ACTIVE);
        if (!optionalProduct.isPresent()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Products not found");
        }

        Product product = optionalProduct.get();

        return new BaseResponseV2(product);

    }

    public BaseResponseV2 findProductById(Long id) throws ApplicationException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Products not found");
        }

        Product product = optionalProduct.get();

        return new BaseResponseV2(product);

    }
    public BaseResponseV2 callbackProduct(Long id,int qty)throws ApplicationException{
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Products not found");
        }
        Product product = optionalProduct.get();
        product.setQty(product.getQty()-qty);
        product.setSold(product.getSold()+qty);
        productRepository.save(product);

        return new BaseResponseV2<>(product);
    }
    @RabbitListener(queues = {"${spring.rabbitmq.queue.product}"})
    private void listenMessage(OrderDTO orderDTO){
        log.info("data " + orderDTO.getProductId());
        callbackProduct(orderDTO.getProductId(),orderDTO.getQty());
    }

}
