package com.tass.productservice.controllers.admin;


import com.tass.common.customanotation.RequireUserLogin;
import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponseV2;
import com.tass.productservice.request.BrandRequest;
import com.tass.productservice.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(path = "/admins/barnd")
public class BrandController {
    @Autowired
    BrandService brandService;
    @RequireUserLogin
    @GetMapping()
    public BaseResponseV2 findAll() throws ApplicationException {
        return brandService.findAll();
    }
    @RequireUserLogin
    @PostMapping()
    public BaseResponseV2 create(@Valid @RequestBody BrandRequest brandRequest) throws ApplicationException {
        return brandService.createBrand(brandRequest);
    }
    @RequireUserLogin
    @PutMapping(path = "/{id}")
    public BaseResponseV2 update(@Valid @RequestBody BrandRequest brandRequest, @PathVariable Long id) throws ApplicationException {
        return brandService.updateBrand(brandRequest,id);
    }
    @RequireUserLogin
    @PutMapping(path = "undelete/{id}")
    public BaseResponseV2 change(@PathVariable Long id) throws ApplicationException {
        return brandService.unDeleteBrand(id);
    }
    @RequireUserLogin
    @PutMapping(path = "/delete/{id}")
    public BaseResponseV2 delete(@PathVariable Long id) throws ApplicationException {
        return brandService.deleteBrand(id);
    }
}
