package com.example.smart_shop.service;

import com.example.smart_shop.databases.entities.Brand;
import com.example.smart_shop.databases.myenum.BrandStatus;
import com.example.smart_shop.databases.repository.BrandRepository;
import com.example.smart_shop.model.request.BrandRequest;
import com.example.smart_shop.model.response.ApiException;
import com.example.smart_shop.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandService {
    @Autowired
    BrandRepository brandRepository;

    public BaseResponse createBrand(BrandRequest brandRequest)throws ApiException{
        Brand brand = new Brand();
        brand.setName(brandRequest.getName());
        brand.setStatus(BrandStatus.ACTIVE);
        brandRepository.save(brand);
        return new BaseResponse<>(200,"Success",brand);
    }
    public BaseResponse updateBrand(BrandRequest brandRequest,Long id)throws ApiException{
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (!brandOptional.isPresent()){
            return new BaseResponse<>(100,"Brand not found");
        }
        Brand brand = brandOptional.get();
        brand.setName(brandRequest.getName());
        brandRepository.save(brand);
        return new BaseResponse<>(200,"Success",brand);
    }
    public BaseResponse deleteBrand(Long id)throws ApiException{
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if(!optionalBrand.isPresent()){
            return new BaseResponse<>(100,"Brand not found");
        }
        Brand brand = optionalBrand.get();
        brand.setStatus(BrandStatus.DEACTIVE);
        brandRepository.save(brand);
        return new BaseResponse<>(200,"Success");
    }
    public BaseResponse unDeleteBrand(Long id)throws ApiException{
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if(!optionalBrand.isPresent()){
            return new BaseResponse<>(100,"Brand not found");
        }
        Brand brand = optionalBrand.get();
        brand.setStatus(BrandStatus.ACTIVE);
        brandRepository.save(brand);
        return new BaseResponse<>(200,"Success");
    }

    public BaseResponse findAll()throws ApiException{
        return new BaseResponse<>(200,"Success",brandRepository.findAll());
    }
}
