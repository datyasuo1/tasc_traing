package com.tass.productservice.services;


import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponseV2;
import com.tass.common.model.ERROR;
import com.tass.common.model.myenums.BrandStatus;
import com.tass.productservice.entities.Brand;
import com.tass.productservice.repositories.BrandRepository;
import com.tass.productservice.request.BrandRequest;
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

    public BaseResponseV2 createBrand(BrandRequest brandRequest) throws ApplicationException {
        Brand brand = new Brand();
        brand.setName(brandRequest.getName());
        brand.setStatus(BrandStatus.ACTIVE);
        brandRepository.save(brand);
        return new BaseResponseV2(brand);
    }

    public BaseResponseV2 updateBrand(BrandRequest brandRequest, Long id) throws ApplicationException {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (!brandOptional.isPresent()) {
            throw new ApplicationException(ERROR.ID_NOT_FOUND, "brand not found");
        }
        Brand brand = brandOptional.get();
        brand.setName(brandRequest.getName());
        brandRepository.save(brand);
        return new BaseResponseV2(brand);
    }

    public BaseResponseV2 deleteBrand(Long id) throws ApplicationException {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (!optionalBrand.isPresent()) {
            throw new ApplicationException(ERROR.ID_NOT_FOUND, "brand not found");
        }
        Brand brand = optionalBrand.get();
        brand.setStatus(BrandStatus.DEACTIVE);
        brandRepository.save(brand);
        return new BaseResponseV2();
    }

    public BaseResponseV2 unDeleteBrand(Long id) throws ApplicationException {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (!optionalBrand.isPresent()) {
            throw new ApplicationException(ERROR.ID_NOT_FOUND, "brand not found");
        }
        Brand brand = optionalBrand.get();
        brand.setStatus(BrandStatus.ACTIVE);
        brandRepository.save(brand);
        return new BaseResponseV2();
    }

    public BaseResponseV2 findAll() throws ApplicationException {
        return new BaseResponseV2(brandRepository.findAll());
    }
}
